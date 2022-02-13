import com.ib.client.EClientSocket;
import com.vg.model.OsAlgoOption;
import com.vg.service.IBBroker;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

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
        String alert5 = "PYPL - $129 - CALL - Feb 4 2022 - 465 @ $1.29 A | $60.01 K | $127.11 | :OSwhite:";

        // When
        OsAlgoOption option1 = broker.createOsAlgoOption(alert1);
        OsAlgoOption option2 = broker.createOsAlgoOption(alert2);
        OsAlgoOption option3 = broker.createOsAlgoOption(alert3);
        OsAlgoOption option4 = broker.createOsAlgoOption(alert4);
        OsAlgoOption option5 = broker.createOsAlgoOption(alert5);

        // Then
        OsAlgoOption expectedOption1 = new OsAlgoOption()
                .symbol("AMD").strike(100).side("PUT").date("20220128").cost(1.60);
        OsAlgoOption expectedOption2 = new OsAlgoOption()
                .symbol("NUE").strike(104).side("PUT").date("20220121").cost(1.06);
        OsAlgoOption expectedOption3 = new OsAlgoOption()
                .symbol("MU").strike(83).side("PUT").date("20220128").cost(1.61);
        OsAlgoOption expectedOption4 = new OsAlgoOption()
                .symbol("V").strike(225).side("CALL").date("20220204").cost(2.70);
        OsAlgoOption expectedOption5 = new OsAlgoOption()
                .symbol("PYPL").strike(129).side("CALL").date("20220204").cost(1.29);

        assertEquals(expectedOption1, option1);
        assertEquals(expectedOption2, option2);
        assertEquals(expectedOption3, option3);
        assertEquals(expectedOption4, option4);
        assertEquals(expectedOption5, option5);
    }



}
