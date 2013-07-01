(ns hbase.config
	(:gen-class)
	(:refer-clojure :exclude [test])
  	(:import [org.apache.hadoop.hbase HBaseConfiguration]
	         [org.apache.hadoop.hbase HBaseTestingUtility]))

(defn new [& options]
	(let [config (HBaseConfiguration/create)]
		(if (not= options nil)
			(doseq [[key value] options]
				(.set config key value)))
		config)) 

(defn test [& options]
	(let [testing-utility (HBaseTestingUtility.)]
		(.startMiniCluster testing-utility 1)
		(let [config (.getConfiguration testing-utility)]
			(if (not= options nil)
				(doseq [[key value] options]
					(.set config key value)))
			config)))

