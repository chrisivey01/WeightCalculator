package com.weight.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/weightLifting")
public class WeightLiftingController {
    private static Logger logger = LoggerFactory.getLogger(WeightLiftingController.class);

    private static double[] weightList = {45, 25, 10, 5, 2.5};


    @RequestMapping(value="/something", method = RequestMethod.GET)
    public List<Double> getSomething(@RequestParam Double weight) {
        double barWeight = 45;

        List<Double> putOnBarbell = new ArrayList<>();

        double left = weight - barWeight;
        while (left != 2.5) {
            for (double weights : weightList) {
                double amt = weights * 2;
                if (amt <= left) {
                    putOnBarbell.add(weights);
                    left -= weights;
                    break;
                }
            }
        }
        putOnBarbell.add(2.5);

        return putOnBarbell;
    }

    @CrossOrigin(origins = "*")
    @RequestMapping(value="/weightCalc", method = RequestMethod.POST)
    public String weightCalc(@RequestParam double weight) {
        double[] plateWeightList = {45, 25, 10, 5, 2.5};
        double barWeight = 45;

        if (weight % plateWeightList[plateWeightList.length - 1] != 0) {
            return "Not possible";
        } else {
            List<Double> weightOnBarbell = new ArrayList<>();

            double remainingWeight = weight - barWeight;

            while (remainingWeight >= plateWeightList[plateWeightList.length - 1] * 2) {
                for (double plateWeight : plateWeightList) {
                    if (remainingWeight >= plateWeight * 2) {
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

    @RequestMapping(value="/calculateDifficultyIncrease", method = RequestMethod.GET)
    public String calculateDifficultyIncrease(@RequestParam int noOfWeeks, @RequestParam List<Integer> weights) {
        List<Integer> newWeights = weights;
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < noOfWeeks; i++) {
            newWeights = getNewWeights(newWeights);
            sb.append("Week ");
            sb.append(i + 1);
            sb.append(" ");
            sb.append(newWeights);
            sb.append(" ");
        }
        return sb.toString();
    }

    @RequestMapping(value="/getNewWeights", method = RequestMethod.GET)
    public List<Integer> getNewWeights(@RequestParam List<Integer> oldWeights) {
        List<Integer> newWeights = new ArrayList<>();
        oldWeights.stream().map(x -> Math.round(x * 1.025))
                .collect(Collectors.toList())
                .forEach(x -> newWeights.add(Integer.parseInt(x.toString())));
        return newWeights;
    }
}
