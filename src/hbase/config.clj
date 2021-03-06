(ns hbase.config
	(:gen-class)
  	(:import [org.apache.hadoop.hbase HBaseConfiguration]))

(defn new [& options]
	(let [config (HBaseConfiguration/create)]
		(if (not= options nil)
			(doseq [[key value] options]
				(.set config key value)))
		config)) 


