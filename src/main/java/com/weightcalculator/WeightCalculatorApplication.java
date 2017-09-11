package com.weightcalculator;
 
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

 
@SpringBootApplication
@RestController
public class WeightCalculatorApplication {
			 private static Logger logger = LoggerFactory.getLogger(WeightCalculatorApplication.class);
	
              static double[] weights = { 45, 25, 10, 5, 2.5 };
              static double barWeight = 45;
             
              public static void main(String[] args) {
                             SpringApplication.run(WeightCalculatorApplication.class, args);
              }
             
              @RequestMapping("/something")
              public List<Double> getsomething(@RequestParam Double weight){
                             List<Double> putOnBarbell = new ArrayList<>();
 
                             double left = weight - barWeight;
                             while (left != 2.5) {
                                           for (int i = 0; i < weights.length; i++) {
                                                          double amt = weights[i] * 2;
                                                          if (amt <= left) {
                                                                        putOnBarbell.add(weights[i]);
                                                                        left -= weights[i];
                                                                        break;
                                                          }
                                           }
                             }
                             putOnBarbell.add(2.5);
 
                             return putOnBarbell;
              }
             
              @CrossOrigin(origins = "*")
              @RequestMapping("/weightCalc")
              public static String weightCalc(@RequestParam double weight) {
                             double[] plateWeightList = { 45, 25, 10, 5, 2.5 };
                             double barWeight = 45;
 
                             if(weight % plateWeightList[plateWeightList.length - 1] != 0){
                                           return "Not possible";
                             }else{
                                           List<Double> weightOnBarbell = new ArrayList<>();
             
                                           double remainingWeight = weight - barWeight;
             
                                           while (remainingWeight >= plateWeightList[plateWeightList.length - 1] * 2) {
                                                          for (double plateWeight : plateWeightList) {
                                                                        if(remainingWeight >= plateWeight * 2){
                                                                                      weightOnBarbell.add(plateWeight);
                                                                                      weightOnBarbell.add(plateWeight);
                                                                                      remainingWeight -= plateWeight * 2;
                                                                                      logger.info("Added 2 {} pound plates", plateWeight);
                                                                                      break;
                                                                        }
                                                          }
                                                         
                                           }
                                           return weightOnBarbell.toString();
                             }
              }
             
              @RequestMapping("/calculateDifficultyIncrease")
              private static String calculateDifficultyIncrease(@RequestParam int noOfWeeks, @RequestParam List<Integer> weights) {
            	  				List<Integer> newWeights = weights;
                             StringBuilder sb = new StringBuilder();
                             for (int i = 0; i < noOfWeeks; i++) {
                                           newWeights = getNewWeights(newWeights);
                                           sb.append("Week " + (i + 1) + " ");
                                           sb.append(newWeights + " ");
                             }
                             return sb.toString();
              }
 
              @RequestMapping("/getNewWeights")
              private static List<Integer> getNewWeights(@RequestParam List<Integer> oldWeights) {
                             List<Integer> newWeights = new ArrayList<>();
                             oldWeights.stream().map(x -> Math.round(x * 1.025))
                                                          .collect(Collectors.toList())
                                                          .forEach(x -> newWeights.add(Integer.parseInt(x.toString())));
                             return newWeights;
              }
              @Configuration
              public class StaticResourceConfiguration extends WebMvcConfigurerAdapter {      
                  @Override
                  public void addResourceHandlers(ResourceHandlerRegistry registry) {
                      registry.addResourceHandler("/pngFiles/**")
                      .addResourceLocations("file:ext-resources/")
                      .setCachePeriod(0);
                  }
              }
}
 