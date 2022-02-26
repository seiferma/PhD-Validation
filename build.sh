#!/bin/sh

# stop on first error
set -e

echo Creating data set
echo
echo This script requires Java 11, Maven, Git, Docker and Zip

# build validation product and create results
echo
echo Building validation application and running it
mvn clean verify -Drelease

# archive validation results
echo 
echo Archiving validation results
mkdir -p target/results
cp products/edu.kit.kastel.dsis.seifermann.phd.validation.product/target/validationresults/*.json target/results

# archive validation docker image
echo 
echo Archiving docker image
mkdir -p target/reproduction
cp products/edu.kit.kastel.dsis.seifermann.phd.validation.product/target/validation.tar target/reproduction/validationApplicationDockerImage.tar

# archive all relevant source code
echo 
echo Archiving source code
mkdir -p target/code
git -c advice.detachedHead=false -C target/code clone -q --branch releases/5.1.0 https://github.com/FluidTrust/Palladio-Supporting-Prolog.git
git -c advice.detachedHead=false -C target/code clone -q --branch releases/1.2.0 https://github.com/FluidTrust/Palladio-Supporting-Prolog4J.git
git -c advice.detachedHead=false -C target/code clone -q --branch releases/5.1.0 https://github.com/FluidTrust/Palladio-Supporting-DataFlowDiagram.git
git -c advice.detachedHead=false -C target/code clone -q --branch releases/5.1.0 https://github.com/FluidTrust/Palladio-Supporting-DataFlowDiagramConfidentiality.git
git -c advice.detachedHead=false -C target/code clone -q --branch releases/5.1.0 https://github.com/FluidTrust/Palladio-Supporting-DataFlowConfidentialityQueryDSL.git
git -c advice.detachedHead=false -C target/code clone -q --branch releases/5.1.0 https://github.com/FluidTrust/Palladio-Addons-DataFlowConfidentiality.git
git -c advice.detachedHead=false -C target/code clone -q --branch releases/5.1.0 https://github.com/FluidTrust/Palladio-Addons-DataFlowConfidentialityQueryDSL.git
git -c advice.detachedHead=false -C target/code clone -q https://github.com/seiferma/PhD-Validation.git

# copy documentation
echo 
echo Copying description
cp -r misc/* target/

# zip file
echo
echo Zipping data set
cd target
zip -q -r ./dataset.zip ./
cd ..

# report success
echo
echo The data set is now available in target/