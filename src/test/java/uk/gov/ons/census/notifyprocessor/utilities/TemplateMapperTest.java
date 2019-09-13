package uk.gov.ons.census.notifyprocessor.utilities;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import org.junit.Test;
import uk.gov.ons.census.notifyprocessor.utilities.TemplateMapper.Tuple;

public class TemplateMapperTest {
  private static final int HOUSEHOLD_ENGLAND = 1;
  private static final int HOUSEHOLD_WALES = 2;
  private static final int HOUSEHOLD_NI = 4;
  private static final int INDIVIDUAL_RESPONSE_ENGLAND = 21;
  private static final int INDIVIDUAL_RESPONSE_WALES = 22;
  private static final int INDIVIDUAL_RESPONSE_NI = 24;

  @Test
  public void testGetTemplate() {

    TemplateMapper underTest =
        new TemplateMapper(
            "TemplateEnglish",
            "TemplateWelsh",
            "TemplateWelshAndEnglish",
            "TemplateNorthernIreland");

    testTemplate(underTest, "UACHHT1", HOUSEHOLD_ENGLAND, "TemplateEnglish");
    testTemplate(underTest, "UACHHT2", HOUSEHOLD_WALES, "TemplateWelshAndEnglish");
    testTemplate(underTest, "UACHHT2W", HOUSEHOLD_WALES, "TemplateWelsh");
    testTemplate(underTest, "UACHHT4", HOUSEHOLD_NI, "TemplateNorthernIreland");
    testTemplate(underTest, "UACIT1", INDIVIDUAL_RESPONSE_ENGLAND, "TemplateEnglish");
    testTemplate(underTest, "UACIT2", INDIVIDUAL_RESPONSE_WALES, "TemplateWelshAndEnglish");
    testTemplate(underTest, "UACIT2W", INDIVIDUAL_RESPONSE_WALES, "TemplateWelsh");
    testTemplate(underTest, "UACIT4", INDIVIDUAL_RESPONSE_NI, "TemplateNorthernIreland");
    assertThat(underTest.getTemplate("Wibble")).isNull();
  }

  private void testTemplate(
      TemplateMapper underTest,
      String fulfilmentCode,
      int expectedQuestionnaireType,
      String expectedTemplate) {
    Tuple actualResult = underTest.getTemplate(fulfilmentCode);

    assertThat(actualResult.getTemplateId()).isEqualTo(expectedTemplate);
    assertThat(actualResult.getQuestionnaireType()).isEqualTo(expectedQuestionnaireType);
  }
}