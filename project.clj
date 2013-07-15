(defproject hbase "0.1.6"
	:description "HBase Access in Clojure"
	:license {:name "Eclipse Public License" :url "http://www.eclipse.org/legal/epl-v10.html"}
	:url "https://github.com/mobiusinversion/hbase"
	:dependencies [
		[org.clojure/clojure "1.5.1"]
		[org.apache.hadoop/hadoop-core "1.2.0"]
		[org.apache.hbase/hbase "0.94.6.1"]
		[org.apache.hadoop/hadoop-test "1.2.0"]
		[org.apache.hbase/hbase "0.94.6.1" :classifier "tests"]]
	:plugins [[lein-marginalia "0.7.1"]])

