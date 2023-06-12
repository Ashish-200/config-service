package ltr.org.config.service;

import com.couchbase.client.core.endpoint.CircuitBreakerConfig;
import com.couchbase.client.core.env.IoConfig;
import com.couchbase.client.core.msg.query.QueryRequest;
import com.couchbase.client.java.Bucket;
import com.couchbase.client.java.Cluster;

import com.couchbase.client.java.Collection;
import com.couchbase.client.java.env.ClusterEnvironment;
import com.couchbase.client.java.json.JsonObject;
import com.couchbase.client.java.kv.GetResult;
import com.couchbase.client.java.query.QueryResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.couchbase.client.core.env.TimeoutConfig;

import java.time.Duration;

@Service
public class ClusterServiceImpl {
    private static final Logger logger = LoggerFactory.getLogger(ClusterServiceImpl.class);
    @Value("${couch.config.username}")
    private String couchUserName;
    @Value("${couch.config.password}")
    private String couchUserPassword;
    @Value("${couch.config.remote.address}")
    private String couchRemoteAddress;
    private Cluster cluster;
    private Map<String, Bucket> buckets = new ConcurrentHashMap<>();

    public String fetchDocumentFromBucket(String bucketName, String documentName) {
        JsonObject content = null;
        Cluster cluster = null;
        try {
            //System.out.println("Before env creation");
            ClusterEnvironment env = ClusterEnvironment.builder().ioConfig(IoConfig.
                            kvCircuitBreakerConfig(CircuitBreakerConfig.builder()
                                    .enabled(true)
                                    .volumeThreshold(45)
                                    .errorThresholdPercentage(25)
                                    .sleepWindow(Duration.ofSeconds(1))
                                    .rollingWindow(Duration.ofMinutes(2))
                            ))
                    .timeoutConfig(TimeoutConfig.kvTimeout(Duration.ofSeconds(30))
                            .queryTimeout(Duration.ofSeconds(10)))
                    .build();
            cluster = Cluster.connect(couchRemoteAddress, couchUserName, couchUserPassword);
            Bucket bucket = cluster.bucket(bucketName);
            bucket.waitUntilReady(Duration.ofSeconds(10));
            Collection collection = bucket.defaultCollection();
            GetResult found = collection.get(documentName);
            content = found.contentAsObject();
            //System.out.println("collection:" + content.toString());
            //System.out.println("parameter setting under process");
            //System.out.println("Cluster created");
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("logging error", e);
        } finally {
            cluster.disconnect();
        }
        return content.toString();
    }

    public String getChildNodeFormDocument(String bucketName, String documentName, String childNode) {
        Cluster cluster = null;
        JsonObject response = null;
        try {
            String query = "select " + childNode + " from " + bucketName + " where meta().id = '" + documentName + "'";
            cluster = Cluster.connect(couchRemoteAddress, couchUserName, couchUserPassword);
            QueryResult result = cluster.query(query);
            response = (JsonObject) result.rowsAsObject().get(0).get(childNode);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("logging error", e);
        } finally {
            if (null != cluster) cluster.disconnect();
        }
        return null != response ? response.toString() : "";
    }
}
