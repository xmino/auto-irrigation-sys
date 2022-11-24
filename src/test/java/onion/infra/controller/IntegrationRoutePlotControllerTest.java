package onion.infra.controller;

import onion.api.service.PlotService;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.any;
import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
//@Ignore("Integration")
public class IntegrationRoutePlotControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private PlotService plotService;

    @Test
    public void listAll_returnValues() throws Exception{

        mockMvc.perform(get("/auto-irrigation/v1/plots")
                                .contentType(MediaType.APPLICATION_JSON))
                                .andExpect(status().isOk())
                                .andExpect(jsonPath("$", hasSize(2)));
    }

    @Test
    public void createPlot_Create() throws Exception{
        mockMvc.perform(post("/auto-irrigation/v1/plots")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content("{\n" +
                                                 "    \"name\":\"Test1\",\n" +
                                                 "    \"cultivatedArea\":\"1000\",\n" +
                                                 "    \"crop\":\"FOOD\"\n" +
                                                 "}"))
               .andExpect(status().isCreated())
               .andExpect(jsonPath("$.id").exists());
    }

    @Test
    public void createPlot_wrongRaw_returnBadRequest() throws Exception{
        mockMvc.perform(post("/auto-irrigation/v1/plots")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content("{\n" +
                                                 "    \"name1\":\"Test1\",\n" +
                                                 "    \"cultivatedArea1\":\"1000\",\n" +
                                                 "    \"cro1p\":\"FOOD\"\n" +
                                                 "}"))
               .andExpect(status().isBadRequest());
    }

    @Test
    public void editPlot_Create() throws Exception{
        mockMvc.perform(put("/auto-irrigation/v1/plots/4fc63668-6a38-11ed-a1eb-0242ac120002")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content("{\n" +
                                                 "    \"name\":\"Test1\",\n" +
                                                 "    \"cultivatedArea\":\"1000\",\n" +
                                                 "    \"crop\":\"FOOD\"\n" +
                                                 "}"))
               .andExpect(status().isOk())
               .andExpect(jsonPath("$.id").exists());
    }

    @Test
    public void editPlot_whenIdDontExist_returnNotFound() throws Exception{
        mockMvc.perform(put("/auto-irrigation/v1/plots/4fc63668-6a38")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content("{\n" +
                                                 "    \"name\":\"Test1\",\n" +
                                                 "    \"cultivatedArea\":\"1000\",\n" +
                                                 "    \"crop\":\"FOOD\"\n" +
                                                 "}"))
               .andExpect(status().isNotFound());
    }

    @Test
    public void configurePlot_addTimeSlotToPlot() throws Exception{
        mockMvc.perform(post("/auto-irrigation/v1/plots/configure/4fc63668-6a38-11ed-a1eb-0242ac120002")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content("{\n" +
                                                 "    \"init\":1604531772,\n" +
                                                 "    \"durationMinutes\":\"20\",\n" +
                                                 "    \"amountWater\":\"100\",\n" +
                                                 "    \"status\":\"PENDING\"\n" +
                                                 "}"))
               .andExpect(status().isOk());
    }

    @Test
    public void configurePlot_whenIdDontExist_returnNotFound() throws Exception{
        mockMvc.perform(post("/auto-irrigation/v1/plots/configure/4fc63668-6a38")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content("{\n" +
                                                 "    \"init\":1604531772,\n" +
                                                 "    \"durationMinutes\":\"20\",\n" +
                                                 "    \"amountWater\":\"100\",\n" +
                                                 "    \"status\":\"PENDING\"\n" +
                                                 "}"))
               .andExpect(status().isNotFound());
    }
}