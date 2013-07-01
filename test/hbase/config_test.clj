(ns hbase.config-test
	(:use [clojure.test])
  	(:require [hbase.config]))

(deftest create-test
	(testing "Creating a new hbase configuration should create an object of the correct type."
		(is (= (type (hbase.config/new)) org.apache.hadoop.conf.Configuration))))



