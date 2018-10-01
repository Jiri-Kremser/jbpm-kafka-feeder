IMAGE?=jkremser/jbpm-kafka-feeder

.PHONY: build
build: package image-build

.PHONY: package
package:
	mvn clean package

.PHONY: image-build
image-build:
	docker build -t $(IMAGE):latest -f Dockerfile .

.PHONY: deploy-jbpm
deploy-jbpm:
	oc apply -f https://raw.githubusercontent.com/jboss-container-images/rhpam-7-openshift-image/7.0.1.GA/rhpam70-image-streams.yaml
	oc apply -f https://raw.githubusercontent.com/jboss-container-images/rhpam-7-openshift-image/7.0.1.GA/templates/rhpam70-trial-ephemeral.yaml
	oc new-app -l app=jbpm -l app.parent=makefile --template=rhpam70-trial-ephemeral -p IMAGE_STREAM_NAMESPACE=`oc project -q`

.PHONY: deploy-kafka
deploy-kafka:
	oc apply -f https://raw.githubusercontent.com/strimzi/strimzi-kafka-operator/0.1.0/kafka-inmemory/resources/openshift-template.yaml
	oc new-app -l app=kafka -l app.parent=makefile --template=strimzi

.PHONY: create-kafka-topic
create-kafka-topic:
	oc exec kafka-0 -- /opt/kafka/bin/kafka-topics.sh --create --topic my-topic --partitions 13 --replication-factor 3 --zookeeper zookeeper:2181

.PHONY: undeploy
undeploy:
	oc delete all -l app.parent=makefile

.PHONY: browser
browser:
	$(eval URL := $(shell oc get route myapp-rhpamcentr --template={{.spec.host}}))
	echo "Opening http://$(URL)"
	echo -e "use these credentials:\n\n  username: adminUser\n  password: RedHat\n\n"
	xdg-open http://$(URL) &> /dev/null
