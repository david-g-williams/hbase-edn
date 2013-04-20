(ns hbase.table
	(:gen-class)
	(:refer-clojure :exclude [get])
	(:import [org.apache.hadoop.hbase.util Bytes]
	         [org.apache.hadoop.hbase.client Put Get HTable]))

(defn connect [name config] 
	(HTable. config name))

(defmulti put (fn[& arglist] (count arglist)))

(defmethod put 4 [self rowkey column-family data]
	(let [p (Put. (Bytes/toBytes rowkey))]
		(doseq [[column-name value] data]
			(.add p
				(Bytes/toBytes (str column-family))
				(Bytes/toBytes (str column-name))
				(Bytes/toBytes (str value))))
		(.put self p)))

(defmethod put 5 [self rowkey column-family column-name value]
    (let [p (Put. (Bytes/toBytes rowkey))]
		(.add p
			(Bytes/toBytes (str column-family))
			(Bytes/toBytes (str column-name))
			(Bytes/toBytes (str value)))
		(.put self p)))

(defn get [self rowkey column-family column-name]
	(let [g (Get. (Bytes/toBytes rowkey))]
		(if (not= column-family nil)
			((.addFamily g (Bytes/toBytes column-family))
			 (if (not= column-name nil)
				(.addColumn g 
					(Bytes/toBytes column-family)
					(Bytes/toBytes column-name))
				()))
			())))



