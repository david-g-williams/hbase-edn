(ns hbase.table-test
	(:use [clojure.test])
	(:require [hbase.config]
	          [hbase.table]
	          [hbase.schema]))

(deftest create-table
	(testing "Creating an Hbase table should create an object of the correct type."
		(def config (hbase.config/create))
		(hbase.schema/create-table "t4" "f1" "f2" "f3" "f4" "f5" config)
		(def table (hbase.table/connect "t4" config))
		(is (= (type table) org.apache.hadoop.hbase.client.HTable))))

(deftest put-four
	(testing "Inserting data with the four argument put should not generate an exception."
		(is (= (hbase.table/put table "k1" "f1" {"c1" "zebra" "c2" "penguin"}) nil))))

(deftest put-five
	(testing "Inserting data with the five argument put should not generate an exception."
		(is (= (hbase.table/put table "k1" "f2" "c1" "panda") nil))))

(deftest get-two
	(testing "Two argument get should return previously inserted values."
		(is (= (get (get (hbase.table/get table "k1") "f1") "c1") "zebra"))))

(deftest get-three
	(testing "Three argument get should return previously inserted values."
		(is (= (get (hbase.table/get table "k1" "f1") "c1") "zebra"))))

(deftest get-four
	(testing "Four argument get should return previously inserted values."
		(is (= (hbase.table/get table "k1" "f1" "c1") "zebra"))))

(deftest scan-two
	(testing "Two argument scan should return the rows between k1 and k2"
		(let [cursor (hbase.table/scan table "k1" "k2")] 
			(loop [result (cursor)]
				(if (not= nil result)
					(let [[row-key hash-map] result]
						(is (= row-key "k1"))
						(is (= (get (get hash-map "f1") "c1") "zebra")))
					(recur (cursor)))))))

(deftest scan-three
	(testing "Three argument scan should return the specified column families for rows between k1 and k2"
		(let [cursor (hbase.table/scan table "k1" "k2" ["f1" "f2"])] 
			(loop [result (cursor)]
				(if (not= nil result)
					(let [[row-key hash-map] result]
						(is (= row-key "k1"))
						(is (= (get (get hash-map "f2") "c1") "panda")))
					(recur (cursor)))))))

(deftest scan-four
	(testing "Four argument scan should return the specified columns for rows between k1 and k2"
		(let [cursor (hbase.table/scan table "k1" "k2" {"f1" ["c2"] "f2" ["c2"]})] 
			(loop [result (cursor)]
				(if (not= nil result)
					(let [[row-key hash-map] result]
						(is (= row-key "k1"))
						(is (= (get (get hash-map "f1") "c2") "penguin")))
					(recur (cursor)))))))

(deftest drop-table
    (testing "Dropping a table should not create an exception"
		(is (= (hbase.schema/drop-table "t4" config) nil))))

(defn test-ns-hook []
	(create-table)
	(put-four)
	(put-five)
	(get-two)
	(get-three)
	(get-four)
	(scan-two)
	(scan-three)
	(scan-four)
	(drop-table))

 


			


