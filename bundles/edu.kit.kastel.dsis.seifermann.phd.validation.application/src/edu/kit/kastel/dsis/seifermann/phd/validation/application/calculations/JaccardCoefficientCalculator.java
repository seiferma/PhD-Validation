package edu.kit.kastel.dsis.seifermann.phd.validation.application.calculations;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.eclipse.emf.compare.Comparison;
import org.eclipse.emf.compare.Diff;
import org.eclipse.emf.compare.DifferenceKind;
import org.eclipse.emf.compare.DifferenceSource;
import org.eclipse.emf.compare.EMFCompare;
import org.eclipse.emf.compare.ReferenceChange;
import org.eclipse.emf.compare.match.DefaultComparisonFactory;
import org.eclipse.emf.compare.match.DefaultEqualityHelperFactory;
import org.eclipse.emf.compare.match.DefaultMatchEngine;
import org.eclipse.emf.compare.match.IComparisonFactory;
import org.eclipse.emf.compare.match.IMatchEngine;
import org.eclipse.emf.compare.match.eobject.IEObjectMatcher;
import org.eclipse.emf.compare.match.impl.MatchEngineFactoryImpl;
import org.eclipse.emf.compare.match.impl.MatchEngineFactoryRegistryImpl;
import org.eclipse.emf.compare.scope.DefaultComparisonScope;
import org.eclipse.emf.compare.util.CompareSwitch;
import org.eclipse.emf.compare.utils.UseIdentifiers;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.ResourceSet;

import com.google.common.collect.Sets;
import com.google.common.collect.Sets.SetView;

public class JaccardCoefficientCalculator {

    public static class JaccardCoefficientRaw {
        private final Map<String, Long> changedElementsByType;
        private final long amountOfElements;

        public JaccardCoefficientRaw(Long amountOfElements, final Map<String, Long> changedElementsByType) {
            var changedElementsByTypeSorted = new TreeMap<String, Long>((k1, k2) -> changedElementsByType.get(k2)
                .compareTo(changedElementsByType.get(k1)));
            changedElementsByTypeSorted.putAll(changedElementsByType);
            this.changedElementsByType = Collections.unmodifiableMap(changedElementsByTypeSorted);
            this.amountOfElements = amountOfElements;
        }

        public Map<String, Long> getChangedElementsByType() {
            return changedElementsByType;
        }

        public long getAmountOfChangedElements() {
            return changedElementsByType.values()
                .stream()
                .mapToLong(Long::longValue)
                .sum();
        }

        public long getAmountOfUnchangedElements() {
            return getAmountOfElements() - getAmountOfChangedElements();
        }

        public long getAmountOfElements() {
            return amountOfElements;
        }

        public double getJaccardCoefficient() {
            return (double) getAmountOfUnchangedElements()
                    / (getAmountOfUnchangedElements() + getAmountOfChangedElements());
        }

    }

    public static class ComparisonResult {
        private Set<EObject> contents1;
        private Set<EObject> contents2;
        private Set<EObject> unchanged1;
        private Set<EObject> unchanged2;
        private Set<EObject> changed1;
        private Set<EObject> changed2;

        public Set<EObject> getContents1() {
            return contents1;
        }

        public void setContents1(Set<EObject> contents1) {
            this.contents1 = contents1;
        }

        public Set<EObject> getContents2() {
            return contents2;
        }

        public void setContents2(Set<EObject> contents2) {
            this.contents2 = contents2;
        }

        public Set<EObject> getUnchanged1() {
            return unchanged1;
        }

        public void setUnchanged1(Set<EObject> unchanged1) {
            this.unchanged1 = unchanged1;
        }

        public Set<EObject> getUnchanged2() {
            return unchanged2;
        }

        public void setUnchanged2(Set<EObject> unchanged2) {
            this.unchanged2 = unchanged2;
        }

        public Set<EObject> getChanged1() {
            return changed1;
        }

        public void setChanged1(Set<EObject> changed1) {
            this.changed1 = changed1;
        }

        public Set<EObject> getChanged2() {
            return changed2;
        }

        public void setChanged2(Set<EObject> changed2) {
            this.changed2 = changed2;
        }

    }

    protected static class ChangedElementsLeftFinder extends CompareSwitch<Collection<EObject>> {

        @Override
        public Collection<EObject> caseReferenceChange(ReferenceChange object) {
            Set<EObject> changes = new HashSet<>(caseDiff(object));
            if (object.getKind() == DifferenceKind.ADD && object.getSource() == DifferenceSource.LEFT) {
                changes.add(object.getValue());
            }
            return changes;
        }

        @Override
        public Collection<EObject> caseDiff(Diff object) {
            return Arrays.asList(object.getMatch()
                .getLeft());
        }

        @Override
        public Collection<EObject> doSwitch(EObject theEObject) {
            return filterProfileElements(super.doSwitch(theEObject));
        }
    }

    protected static class ChangedElementsRightFinder extends CompareSwitch<Collection<EObject>> {

        @Override
        public Collection<EObject> caseReferenceChange(ReferenceChange object) {
            Set<EObject> changes = new HashSet<>(caseDiff(object));
            if (object.getKind() == DifferenceKind.DELETE && object.getSource() == DifferenceSource.LEFT) {
                changes.add(object.getValue());
            }
            return changes;
        }

        @Override
        public Collection<EObject> caseDiff(Diff object) {
            return Arrays.asList(object.getMatch()
                .getRight());
        }

        @Override
        public Collection<EObject> doSwitch(EObject theEObject) {
            return filterProfileElements(super.doSwitch(theEObject));
        }

    }

    private static final ChangedElementsLeftFinder changedElementsLeftFinder = new ChangedElementsLeftFinder();
    private static final ChangedElementsRightFinder changedElementsRightFinder = new ChangedElementsRightFinder();
    private final ResourceSet rs1;
    private final ResourceSet rs2;
    private final EMFCompare emfCompare;

    public JaccardCoefficientCalculator(ResourceSet rs1, ResourceSet rs2) {
        this.rs1 = rs1;
        this.rs2 = rs2;
        this.emfCompare = createEMFCompareInstance();
    }

    public ComparisonResult compare() {
        DefaultComparisonScope scope = new DefaultComparisonScope(rs1, rs2, null);
        Comparison comparison = emfCompare.compare(scope);

        // identify changes
        Set<EObject> rs1Changed = new HashSet<>();
        Set<EObject> rs2Changed = new HashSet<>();
        for (Diff diff : comparison.getDifferences()) {
            rs1Changed.addAll(changedElementsLeftFinder.doSwitch(diff));
            rs2Changed.addAll(changedElementsRightFinder.doSwitch(diff));
        }
        rs2Changed.remove(null);
        rs1Changed.remove(null);

        // build element sets for metric calculation
        Set<EObject> contents1 = getAllContents(rs1);
        Set<EObject> contents2 = getAllContents(rs2);
        SetView<EObject> unchanged1 = Sets.difference(contents1, rs1Changed);
        SetView<EObject> unchanged2 = Sets.difference(contents2, rs2Changed);
        if (unchanged1.size() != unchanged2.size()) {
            throw new IllegalStateException("The number of unchanged elements has to be identical between two models.");
        }

        ComparisonResult result = new ComparisonResult();
        result.setContents1(contents1);
        result.setContents2(contents2);
        result.setUnchanged1(unchanged1);
        result.setUnchanged2(unchanged2);
        result.setChanged1(rs1Changed);
        result.setChanged2(rs2Changed);
        return result;
    }

    public static JaccardCoefficientRaw calculateJaccardCoefficient(ComparisonResult comparisonResult) {
        var changedElements = countChangedElementsByType(comparisonResult);
        var totalElements = comparisonResult.getUnchanged1()
            .size()
                + comparisonResult.getChanged1()
                    .size()
                + comparisonResult.getChanged2()
                    .size();
        return new JaccardCoefficientRaw((long) totalElements, changedElements);
    }

    private static Map<String, Long> countChangedElementsByType(ComparisonResult comparisonResult) {
        return Stream.concat(comparisonResult.getChanged1()
            .stream(),
                comparisonResult.getChanged2()
                    .stream())
            .map(EObject::eClass)
            .map(EClass::getName)
            .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));
    }

    private static Set<EObject> getAllContents(ResourceSet rs) {
        Set<EObject> contents = new HashSet<>();
        rs.getAllContents()
            .forEachRemaining(n -> {
                if (n instanceof EObject) {
                    var eObject = (EObject) n;
                    if (!belongsToProfile(eObject)) {
                        contents.add((EObject) n);
                    }
                }
            });
        return contents;
    }

    private static EMFCompare createEMFCompareInstance() {
        IEObjectMatcher fallBackMatcher = DefaultMatchEngine.createDefaultEObjectMatcher(UseIdentifiers.WHEN_AVAILABLE);
        IComparisonFactory comparisonFactory = new DefaultComparisonFactory(new DefaultEqualityHelperFactory());
        IMatchEngine.Factory.Registry registry = MatchEngineFactoryRegistryImpl.createStandaloneInstance();
        @SuppressWarnings("deprecation")
        final MatchEngineFactoryImpl matchEngineFactory = new MatchEngineFactoryImpl(fallBackMatcher,
                comparisonFactory);
        matchEngineFactory.setRanking(20);
        registry.add(matchEngineFactory);
        return EMFCompare.builder()
            .setMatchEngineFactoryRegistry(registry)
            .build();
    }

    private static boolean belongsToProfile(EObject eObject) {
        return eObject != null && eObject.eResource()
            .getURI()
            .lastSegment()
            .endsWith("emfprofile_diagram");
    }

    private static <T extends EObject> Collection<T> filterProfileElements(Collection<T> collection) {
        return collection.stream()
            .filter(o -> !belongsToProfile(o))
            .collect(Collectors.toList());
    }

}
