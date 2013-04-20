(ns hbase.config-test
  (:require [clojure.test]
            [hbase.config]))

(clojure.test/deftest create-test
	(clojure.test/testing "Create a new hbase configuration."
		(clojure.test/is
			(= 
				(type (hbase.config/create))
				org.apache.hadoop.conf.Configuration))))






