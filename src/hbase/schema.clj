(ns hbase.schema
	(:gen-class)
	(:import [org.apache.hadoop.hbase HTableDescriptor]
			 [org.apache.hadoop.hbase HColumnDescriptor]
			 [org.apache.hadoop.hbase.client HBaseAdmin]))

(defn create-table [config table-name & column-families]
	(let [admin (HBaseAdmin. config)]))


