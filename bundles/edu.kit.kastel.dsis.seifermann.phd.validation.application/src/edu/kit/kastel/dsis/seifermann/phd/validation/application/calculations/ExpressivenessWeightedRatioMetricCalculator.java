package edu.kit.kastel.dsis.seifermann.phd.validation.application.calculations;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

import edu.kit.kastel.dsis.seifermann.phd.validation.application.dto.RatioDTO;
import edu.kit.kastel.dsis.seifermann.phd.validation.models.ConfidentialityMechanism;
import edu.kit.kastel.dsis.seifermann.phd.validation.models.Model;

public class ExpressivenessWeightedRatioMetricCalculator {

    private final Collection<Model> models;

    public ExpressivenessWeightedRatioMetricCalculator(Collection<? extends Model> models) {
        this.models = Collections.unmodifiableCollection(models);
    }

    public static double calculateWeightedRatioMetric(Map<ConfidentialityMechanism, RatioDTO> expressedModelsRatios) {
        double ratioSum = expressedModelsRatios.values()
            .stream()
            .mapToDouble(ratio -> ratio.getAmount() / (double) ratio.getTotal())
            .sum();
        int ratioAmount = expressedModelsRatios.size();
        double weightedRatio = ratioSum / ratioAmount;
        return weightedRatio;
    }

    public Map<ConfidentialityMechanism, RatioDTO> getExpressedModelsRatios() {
        var result = new HashMap<ConfidentialityMechanism, RatioDTO>();

        var modelsByMechanism = models.stream()
            .collect(Collectors.groupingBy(Model::getMechanism));

        for (var mechanismEntry : modelsByMechanism.entrySet()) {
            var modeled = (int) mechanismEntry.getValue()
                .stream()
                .filter(Model::hasModel)
                .count();
            var total = mechanismEntry.getValue()
                .size();
            var ratio = new RatioDTO(modeled, total);
            result.put(mechanismEntry.getKey(), ratio);
        }

        return result;
    }

}
