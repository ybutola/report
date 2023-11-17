package com.butola.report.service.exhibits;

import java.io.IOException;

import com.butola.report.service.exhibits.exhibitb.AssetsGenerator;
import com.butola.report.service.exhibits.exhibitb.ExhibitBGenerator;
import com.butola.report.service.exhibits.exhibitb.LiabilitiesGenerator;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.core.io.ResourceLoader;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = {ExhibitBGenerator.class})
@ExtendWith(SpringExtension.class)
class ExhibitBGeneratorTest {
    @Autowired
    private ExhibitBGenerator exhibitBGenerator;

    @MockBean
    private ResourceLoader resourceLoader;

    @MockBean
    private AssetsGenerator assetsGenerator;

    @MockBean
    private LiabilitiesGenerator liabilitiesGenerator;

    /**
     * Method under test: {@link ExhibitBGenerator#readTriaBalance()}
     */
    @Test
    @Disabled("TODO: Complete this test")
    void testReadTriaBalance() throws IOException {
        boolean test=true;
        exhibitBGenerator.readTriaBalance();
        assert test;
    }
}

