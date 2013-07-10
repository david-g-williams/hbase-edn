(ns hbase.table
	(:gen-class)
	(:refer-clojure :exclude [get])
	(:import [clojure.lang PersistentVector PersistentArrayMap] 
	         [org.apache.hadoop.hbase.util Bytes]
	         [org.apache.hadoop.hbase.client Put Get HTable Scan]))

;; todo 
;; put 3 (done)
;; put with timestamp
;; get with timestamp(s)
;; delete 

(defn new [name config] 
	(HTable. config name))

(defn create-put [rowkey] 
	(Put. (Bytes/toBytes rowkey)))

(defn create-get [rowkey] 
	(Get. (Bytes/toBytes rowkey)))

(defmulti put (fn[& arglist] (count arglist)))

(defmethod put 3 [self rowkey payload]
	(let [p (create-put rowkey)]
		(doseq [[column-family data] payload]
			(let [column-family-bytes (Bytes/toBytes (str column-family))]
				(doseq [[column-name value] data]
					(.add p
						column-family-bytes
						(Bytes/toBytes (str column-name))
						(Bytes/toBytes value)))))
		(.put self p)))

(defmethod put 4 [self rowkey column-family data]
	(let [p (create-put rowkey)]
		(doseq [[column-name value] data]
			(.add p
				(Bytes/toBytes (str column-family))
				(Bytes/toBytes (str column-name))
				(Bytes/toBytes value)))
		(.put self p)))

(defmethod put 5 [self rowkey column-family column-name value]
    (let [p (create-put rowkey)]
		(.add p
			(Bytes/toBytes (str column-family))
			(Bytes/toBytes (str column-name))
			(Bytes/toBytes value))
		(.put self p)))

(defn get-to-map [result]
	(let [key-values (seq (.raw result)) return (ref {})]
		(doseq [key-value key-values]
			(dosync
				(alter return assoc-in [
					(Bytes/toString (.getFamily key-value))
					(Bytes/toString (.getQualifier key-value))]
					(Bytes/toString (.getValue key-value)))))
		@return))

(defmulti get (fn [& arglist] (count arglist)))

(defmethod get 2 [self rowkey]
	(let [get-operation (create-get rowkey)]
		(get-to-map
			(.get self get-operation))))

(defmethod get 3 [self rowkey column-family]
	(let [get-operation (create-get rowkey)]
		(.addFamily get-operation (Bytes/toBytes ^String column-family))
		(clojure.core/get
			(get-to-map 
				(.get self get-operation)) 
			column-family)))

(defmethod get 4 [self rowkey column-family column-name]
	(let [get-operation (create-get rowkey)]
		(.addColumn get-operation
			(Bytes/toBytes column-family)
			(Bytes/toBytes column-name))
		(clojure.core/get 
			(clojure.core/get 
				(get-to-map 
					(.get self get-operation)) 
				column-family) 
			column-name)))

(defn result-scan-iterator [result-scanner]
	#(let [result (.next result-scanner)]
		(if (not= result nil)
			[(Bytes/toString (.getRow result)) (get-to-map result)]
			nil)))

(defmulti scan (fn [table & args] (map class args)))

(defmethod scan [String String] [table start-key end-key]
	(let [scan (Scan. (Bytes/toBytes start-key) (Bytes/toBytes end-key)) result-scanner (.getScanner table scan)]
		(result-scan-iterator result-scanner)))

(defmethod scan [String String PersistentVector] [table start-key end-key column-families]
	(let [scan (Scan. (Bytes/toBytes start-key) (Bytes/toBytes end-key))]
		(doseq [family column-families]
			(.addFamily scan (Bytes/toBytes family)))
		(result-scan-iterator (.getScanner table scan))))

(defmethod scan [String String PersistentArrayMap] [table start-key end-key column-map]
	(let [scan (Scan. (Bytes/toBytes start-key) (Bytes/toBytes end-key))]
		(doseq [[column-family column-names] column-map]
			(let [column-family-bytes (Bytes/toBytes column-family)]
				(doseq [column-name column-names]
					(.addColumn scan column-family-bytes (Bytes/toBytes column-name)))))
		(result-scan-iterator (.getScanner table scan))))











