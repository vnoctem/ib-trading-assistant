import com.ib.client.EClientSocket;
import com.vg.model.IBOption;
import com.vg.service.IBBroker;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class IBBrokerTest {

    private IBBroker broker;
    private EClientSocket client;

    @Test
    public void Given_FiveAlerts_When_CreatingOsAlgoOption_Then_AssertResult() {

        broker = new IBBroker(client);

        // Given
        String alert1 = "AMD - $100 - PUT - Jan 28 2022 - 152 @ $1.60 A | $24.47 K | $100.01 | :OSwhite:";
        String alert2 = "NUE - $104 - PUT - Jan 21 2022 - 399 @ $1.06 A | $42.29 K | $104.69 | :OSwhite:";
        String alert3 = "MU - $83 - PUT - Jan 28 2022 - 152 @ $1.61 A | $24.47 K | $83.61 | :OSwhite:";
        String alert4 = "V - $225 - CALL - Feb 4 2022 - 112 @ $2.70 A | $30.25 K | $223.69 | :OSwhite:";
        String alert5 = "FB - $207.50 - CALL - Feb 25 2022 - 181 @ $4.01 A | $72.74 K | $206.67 | :OSwhite:";

        // When
        IBOption option1 = broker.createIBOption(alert1);
        IBOption option2 = broker.createIBOption(alert2);
        IBOption option3 = broker.createIBOption(alert3);
        IBOption option4 = broker.createIBOption(alert4);
        IBOption option5 = broker.createIBOption(alert5);

        // Then
        IBOption expectedOption1 = new IBOption()
                .symbol("AMD").strike(100).side("PUT").date("20220128").cost(1.60);
        IBOption expectedOption2 = new IBOption()
                .symbol("NUE").strike(104).side("PUT").date("20220121").cost(1.06);
        IBOption expectedOption3 = new IBOption()
                .symbol("MU").strike(83).side("PUT").date("20220128").cost(1.61);
        IBOption expectedOption4 = new IBOption()
                .symbol("V").strike(225).side("CALL").date("20220204").cost(2.70);
        IBOption expectedOption5 = new IBOption()
                .symbol("FB").strike(207.5).side("CALL").date("20220225").cost(4.01);

        assertEquals(expectedOption1, option1);
        assertEquals(expectedOption2, option2);
        assertEquals(expectedOption3, option3);
        assertEquals(expectedOption4, option4);
        assertEquals(expectedOption5, option5);
    }



}
