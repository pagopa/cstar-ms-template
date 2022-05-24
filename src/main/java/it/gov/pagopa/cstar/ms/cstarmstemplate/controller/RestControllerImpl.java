package it.gov.pagopa.cstar.ms.cstarmstemplate.controller;

import it.gov.pagopa.cstar.ms.cstarmstemplate.model.FileMetadata;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@ResponseBody
@Slf4j
public class RestControllerImpl implements TemplateRestController {

  @Override
  public void healthCheck() {
    //Return OK if the service is reachable
  }

  @Override
  public String greetings(String name) {
    return "Hello " + name + "!";
  }

  @Override
  public String echoServer(@RequestBody FileMetadata file) {
    return "Server: received " + file.toString();
  }
}
