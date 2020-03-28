package br.com.celk.evaluation.ufengine.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import br.com.celk.evaluation.ufengine.web.rest.TestUtil;

public class CountyTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(County.class);
        County county1 = new County();
        county1.setId(1L);
        County county2 = new County();
        county2.setId(county1.getId());
        assertThat(county1).isEqualTo(county2);
        county2.setId(2L);
        assertThat(county1).isNotEqualTo(county2);
        county1.setId(null);
        assertThat(county1).isNotEqualTo(county2);
    }
}
