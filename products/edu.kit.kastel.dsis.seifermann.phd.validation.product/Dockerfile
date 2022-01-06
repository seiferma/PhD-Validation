FROM openjdk:11-jre

ADD target/products/edu.kit.kastel.dsis.seifermann.phd.validation.product/linux/gtk/x86_64 /opt/eclipse

WORKDIR "/opt/eclipse"
VOLUME ["/tmp/result"]

ENTRYPOINT ["./eclipse"]
CMD ["/tmp/result"]

