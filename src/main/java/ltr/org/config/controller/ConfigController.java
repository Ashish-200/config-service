package ltr.org.config.controller;

import ltr.org.config.service.ClusterServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping("/configService")
public class ConfigController {
    @Autowired
    ClusterServiceImpl service;
    private static final Logger logger = LoggerFactory.getLogger(ConfigController.class);

    @RequestMapping(method = RequestMethod.GET, value = "/get/{bucketName}/{documentName}")
    public ResponseEntity<String> getBlog(@PathVariable String bucketName, @PathVariable String documentName) {
        logger.debug("Request variables:bucketName" + bucketName + "Document Name" + documentName);
        logger.debug("response" + service.fetchDocumentFromBucket(bucketName, documentName));
        return new ResponseEntity<>(service.fetchDocumentFromBucket(bucketName, documentName), HttpStatus.OK);

    }

    @GetMapping(value = "/get/{bucketName}/{documentName}/{childNode}")
    public ResponseEntity<String> getNode(@PathVariable String bucketName,
                                          @PathVariable String documentName,
                                          @PathVariable String childNode) {
        logger.debug("Request variables: BucketName {} Document Name {} and childNode name {}", bucketName, documentName, childNode);
        String response = service.getChildNodeFormDocument(bucketName, documentName, childNode);
        logger.debug("Response received is: {}", response);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/get/{documentName}")
    public ResponseEntity<String> getRespose(@PathVariable String documentName) {
        logger.debug("Request variables:bucketName Document Name :" + documentName);

        return new ResponseEntity<>("Hello I am there at config Service "+documentName, HttpStatus.OK);

    } //comment
}
