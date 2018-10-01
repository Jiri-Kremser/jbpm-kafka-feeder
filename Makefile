IMAGE?=jkremser/jbpm-kafka-feeder

.PHONY: build
build: package image-build

.PHONY: package
package:
	mvn clean package

.PHONY: image-build
image-build:
	docker build -t $(IMAGE):latest -f Dockerfile .
