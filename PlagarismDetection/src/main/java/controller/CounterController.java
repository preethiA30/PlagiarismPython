package controller;

import model.Counter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import service.counter.CounterServiceImpl;

import java.util.List;


/**
 * Router for Controller
 */
@CrossOrigin(origins = "http://localhost:4200")
@RestController
public class CounterController {


    /**
     * CourseServices
     */
    private CounterServiceImpl counterService;


    public CounterController(CounterServiceImpl counterService) {
        this.counterService = counterService;
    }

    /**
     * Gets course for given UserID
     *
     * @return the List of CourseService for the given User
     */
    @RequestMapping(value = "/api/statistics", method = RequestMethod.GET)
    public ResponseEntity<List<Counter>> getStatistics() {
        try {
            List<Counter> counterList = counterService.getStatistics();
            return new ResponseEntity(counterList, HttpStatus.ACCEPTED);
        } catch (Exception exp) {
            return new ResponseEntity(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }




}
