(ns hbase.schema
	(:gen-class)
	(:import [org.apache.hadoop.hbase HTableDescriptor HColumnDescriptor]
			 [org.apache.hadoop.hbase.client HBaseAdmin]))

(defn create-table [table-name & arguments]
	(let [arguments (vec arguments) admin (HBaseAdmin. (last arguments)) descriptor (HTableDescriptor. table-name) column-families (pop arguments)]
		(doseq  [column-family column-families]
			(.addFamily descriptor
				(HColumnDescriptor. column-family)))
		(try
			(.createTable admin descriptor)
			(catch Exception e 
				(prn 
					(str "create-table exception: " (.getMessage e)))))))

(defn drop-table [config table-name])


