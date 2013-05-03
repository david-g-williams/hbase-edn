(ns hbase.table-test
	(:import [org.apache.hadoop.hbase KeyValue]
		[org.apache.hadoop.hbase.util Bytes])
	(:require [clojure.test]
		[hbase.config]
		[hbase.table]
		[hbase.schema]))

(def config 
	(hbase.config/create))

(hbase.schema/create-table "t4" "f1" "f2" "f3" "f4" "f5" config)

(def table 
	(hbase.table/connect "t4" config))
 
(clojure.test/deftest connect
	(clojure.test/testing "HBase table create."
		(clojure.test/is 
			(= 
				(type table)  
				org.apache.hadoop.hbase.client.HTable))))

(clojure.test/deftest put-4
	(clojure.test/testing "Test put operation with hash-map data"
		(clojure.test/is
			(=
				(hbase.table/put table "k1" "f1" {"c1" "zebra" "c2" "penguin"})
				nil))))

(clojure.test/deftest put-5
	(clojure.test/testing "Test put operation with a single key value pair"
		(clojure.test/is
			(=
				(hbase.table/put table "k1" "f2" "c1" "panda")
				nil))))

(clojure.test/deftest get-2
	(clojure.test/testing "Get hbase record by table and rowkey"
		(clojure.test/is 
			(=
				(get 
					(get 
						(hbase.table/get table "k1") 
						"f1") 
					"c1")
			 	"zebra"))))

(clojure.test/deftest get-3
	(clojure.test/testing "Get hbase record by table, rowkey and column-family"
		(clojure.test/is 
			(=
				(get 
					(hbase.table/get table "k1" "f1") 
					"c1")
			 	"zebra"))))

(clojure.test/deftest get-4
	(clojure.test/testing "Get hbase record by table, rowkey, column-family and column-name"
		(clojure.test/is 
			(=
				(hbase.table/get table "k1" "f1" "c1")
			 	"zebra"))))

(clojure.test/deftest scan-2
	(clojure.test/testing "Scan rows between k1 and k2"
		(let [cursor (hbase.table/scan table "k1" "k2")] 
			(loop [result (cursor)]
				(when (not= nil result)
				(let [[row-key hash-map] result]
					(clojure.test/is
						(= row-key "k1"))
					(clojure.test/is
						(=
							(get
								(get hash-map "f1")
								"c1")
							"zebra")))
				(recur (cursor)))))))

(clojure.test/deftest scan-3
	(clojure.test/testing "Scan rows between k1 and k2"
		(let [cursor (hbase.table/scan table "k1" "k2" ["f1" "f2"])] 
			(loop [result (cursor)]
				(when (not= nil result)
				(let [[row-key hash-map] result]
					(clojure.test/is 
						(= row-key "k1"))
					(clojure.test/is
						(=
							(get
								(get hash-map "f2")
								"c1")
							"panda")))
				(recur (cursor)))))))

(clojure.test/deftest scan-4
	(clojure.test/testing "Scan rows between k1 and k2"
		(let [cursor (hbase.table/scan table "k1" "k2" {"f1" ["c2"] "f2" ["c2"]})] 
			(loop [result (cursor)]
				(when (not= nil result)
				(let [[row-key hash-map] result]
					(clojure.test/is
						(= row-key "k1"))
					(clojure.test/is
						(=
							(get
								(get hash-map "f1")
								"c2")
							"penguin")))
				(recur (cursor)))))))




			






