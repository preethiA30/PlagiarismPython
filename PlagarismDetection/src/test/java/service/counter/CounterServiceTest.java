package service.counter;

import com.fasterxml.jackson.databind.ObjectMapper;
import dao.CounterDaoImpl;
import model.Counter;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

/**
 * Tests for all services related to Report statistics
 */
@RunWith(MockitoJUnitRunner.class)
public class CounterServiceTest {

    private MockMvc mvc;
    private Counter counterExample;

    @InjectMocks
    private CounterServiceImpl counterService;

    @Mock
    CounterDaoImpl counterDao;

    @Before
    public void setup(){
        JacksonTester.initFields(this, new ObjectMapper());
        mvc = MockMvcBuilders.standaloneSetup(counterService).build();
        counterService=new CounterServiceImpl(counterDao);
        counterExample= new Counter();
    }

    /**
     * Test get statistics by setting only the date field
     */
    @Test
    public void getStatsWithDate(){
        List<Counter> counter = new ArrayList<>();
        counterExample.setDate("2018/04/05");
        counter.add(counterExample);
        when(counterDao.getStatistics()).thenReturn(counter);
        assertEquals(counterService.getStatistics(),counter);
    }

    /**
     * Test get statistics by setting only the count field
     */
    @Test
    public void getStatsWithCount(){
        List<Counter> counter = new ArrayList<>();
        counterExample.setCount(2);
        counter.add(counterExample);
        when(counterDao.getStatistics()).thenReturn(counter);
        assertEquals(counterService.getStatistics(),counter);
    }

    /**
     * Test get statistics by setting only the result count field
     */
    @Test
    public void getStatsWithResultCount(){
        List<Counter> counter = new ArrayList<>();
        counterExample.setResultCount(10);
        counter.add(counterExample);
        when(counterDao.getStatistics()).thenReturn(counter);
        assertEquals(counterService.getStatistics(),counter);
    }

    /**
     * Test get statistics by setting all the field
     */
    @Test
    public void getStatsWithAllFields(){
        List<Counter> counter = new ArrayList<>();
        counterExample.setDate("2018/04/02");
        counterExample.setCount(4);
        counterExample.setResultCount(20);
        counter.add(counterExample);
        when(counterDao.getStatistics()).thenReturn(counter);
        assertEquals(counterService.getStatistics(),counter);
    }

    /**
     * Test for retrieving the date field
     */
    @Test
    public void checkDateField(){
        List<Counter> counter = new ArrayList<>();
        counterExample.setDate("2018/04/09");
        counter.add(counterExample);
        when(counterDao.getStatistics()).thenReturn(counter);
        assertEquals(counter,counterService.getStatistics());
        assertEquals("2018/04/09",counterExample.getDate());
    }

    /**
     * Test for retrieving the count field
     */
    @Test
    public void checkCountField(){
        List<Counter> counter = new ArrayList<>();
        counterExample.setCount(9);
        counter.add(counterExample);
        when(counterDao.getStatistics()).thenReturn(counter);
        assertEquals(counter,counterService.getStatistics());
        assertEquals(9,counterExample.getCount());
    }

    /**
     * Test for retrieving the result count field
     */
    @Test
    public void checkResultCountField(){
        List<Counter> counter = new ArrayList<>();
        counterExample.setResultCount(20);
        counter.add(counterExample);
        when(counterDao.getStatistics()).thenReturn(counter);
        assertEquals(counter,counterService.getStatistics());
        assertEquals(20,counterExample.getResultCount());
    }
}
