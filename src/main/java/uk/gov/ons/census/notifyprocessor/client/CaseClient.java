package uk.gov.ons.census.notifyprocessor.client;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;
import uk.gov.ons.census.notifyprocessor.model.CaseDetailsDTO;
import uk.gov.ons.census.notifyprocessor.model.UacQidDTO;

@Component
public class CaseClient {

  private final RestTemplate restTemplate;

  @Value("${caseapi.host}")
  private String host;

  @Value("${caseapi.port}")
  private String port;

  public CaseClient(RestTemplate restTemplate) {
    this.restTemplate = restTemplate;
  }

  public UacQidDTO getUacQid(String caseId, int questionnaireType) {
    UriComponents uri =
        UriComponentsBuilder.newInstance()
            .scheme("http")
            .host(host)
            .port(port)
            .path("/uacqid/create")
            .build()
            .encode();
    CaseDetailsDTO caseDetails = new CaseDetailsDTO();
    caseDetails.setCaseId(caseId);
    caseDetails.setQuestionnaireType(Integer.toString(questionnaireType));

    UacQidDTO uacQid = restTemplate.postForObject(uri.toUri(), caseDetails, UacQidDTO.class);
    return uacQid;
  }
}