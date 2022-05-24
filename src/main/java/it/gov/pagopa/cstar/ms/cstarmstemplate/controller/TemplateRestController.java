package it.gov.pagopa.cstar.ms.cstarmstemplate.controller;

import io.swagger.annotations.Api;
import it.gov.pagopa.cstar.ms.cstarmstemplate.model.FileMetadata;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Controller to expose ReST interface
 */
@Api(tags = "ReST Controller")
@RequestMapping("")
@Validated
public interface TemplateRestController {

  @GetMapping(value = "/")
  @ResponseStatus(HttpStatus.OK)
  void healthCheck();

  @GetMapping(value = "/hello")
  String greetings(@RequestParam(value = "name") String name);

  @PutMapping(value = "/file")
  String echoServer(@RequestBody() FileMetadata file);
}
