package gr.publicsoft.springbootcrud;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import gr.publicsoft.springbootcrud.model.Supplier;
import gr.publicsoft.springbootcrud.repository.SupplierRepository;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@ActiveProfiles("test")
@ExtendWith(SpringExtension.class)
@AutoConfigureMockMvc
@Transactional
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class SupplierIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private SupplierRepository supplierRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeAll
    void setUp() {
        var sup1 = new Supplier();
        sup1.setCompanyName("sup 1");
        sup1.setVatNumber("1234567890");
        var sup2 = new Supplier();
        sup2.setCompanyName("sup 2");
        sup2.setVatNumber("9876543210");
        supplierRepository.save(sup1);
        supplierRepository.save(sup2);
    }

    @Test
    public void getAllSuppliers() throws Exception {
        mockMvc.perform(get("/api/suppliers"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$._embedded.suppliers").isArray())
                .andExpect(jsonPath("$.page.totalElements").value(2));
    }

    @Test
    public void getSingleSupplier() throws Exception {
        mockMvc.perform(get("/api/suppliers/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.companyName").value("sup 1"))
                .andExpect(jsonPath("$.vatNumber").value("1234567890"));
    }

    @Test
    public void searchSuppliers() throws Exception {
        mockMvc.perform(get("/api/suppliers/search/findByQuery?query=123456"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$._embedded.suppliers").isArray())
                .andExpect(jsonPath("$._embedded.suppliers[0].companyName").value("sup 1"));

        mockMvc.perform(get("/api/suppliers/search/findByQuery?query=sup 1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$._embedded.suppliers[0].vatNumber").value("1234567890"));
    }

    @Test
    public void createSupplier() throws Exception {
        var newSup = new Supplier();
        newSup.setCompanyName("new sup");
        newSup.setVatNumber("999999999");

        mockMvc.perform(post("/api/suppliers")
                .contentType(MediaType.APPLICATION_JSON)
                .content(convertToJsonString(newSup)))
                .andExpect(status().isCreated());

        mockMvc.perform(get("/api/suppliers/search/findByQuery?query=999999999"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$._embedded.suppliers[0].companyName").value("new sup"));
    }

    @Test
    public void deleteSupplier() throws Exception {
        mockMvc.perform(get("/api/suppliers/1"))
                .andExpect(status().isOk());

        mockMvc.perform(delete("/api/suppliers/1"))
                .andExpect(status().isNoContent());

        mockMvc.perform(get("/api/suppliers/1"))
                .andExpect(status().isNotFound());
    }

    @Test
    public void getSupplierThatDoesNotExist() throws Exception {
        mockMvc.perform(get("/api/suppliers/99999"))
            .andExpect(status().isNotFound());
    }

    @Test
    public void searchSuppliersThatDoNotExist() throws Exception {
        mockMvc.perform(get("/api/suppliers/search/findByQuery?query=qweqweqweqwe"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$._embedded.suppliers").isArray())
            .andExpect(jsonPath("$._embedded.suppliers").isEmpty());
    }


    private String convertToJsonString(final Object obj) throws JsonProcessingException {
        return objectMapper.writeValueAsString(obj);
    }

}
