(ns hbase.table-test
	(:import [org.apache.hadoop.hbase KeyValue]
		[org.apache.hadoop.hbase.util Bytes])
	(:require [clojure.test]
		[hbase.config]
		[hbase.table]))

(def config 
	(hbase.config/create))

(def table 
	(hbase.table/connect "t1" config))
 
(clojure.test/deftest connect
	(clojure.test/testing "HBase table create."
		(clojure.test/is 
			(= 
				(type table)  
				org.apache.hadoop.hbase.client.HTable))))

(clojure.test/deftest put-1
	(clojure.test/testing "Test put operation with hash-map data"
		(clojure.test/is
			(=
				(hbase.table/put table "k1" "f2" {"c2" "zebra"})
				nil))))

(clojure.test/deftest put-2
	(clojure.test/testing "Test put operation with a single key value pair"
		(clojure.test/is
			(=
				(hbase.table/put table "k1" "f2"  "c3" "panda")
				nil))))


(prn 
	(hbase.table/get table "k1"))

(prn
    (hbase.table/get table "k1", "f2"))

(prn
    (hbase.table/get table "k1", "f2", "c3"))

