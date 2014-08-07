#!/bin/bash

# Licensed to the Apache Software Foundation (ASF) under one or more
# contributor license agreements.  See the NOTICE file distributed with
# this work for additional information regarding copyright ownership.
# The ASF licenses this file to You under the Apache License, Version 2.0
# (the "License"); you may not use this file except in compliance with
# the License.  You may obtain a copy of the License at
#
#     http://www.apache.org/licenses/LICENSE-2.0
#
# Unless required by applicable law or agreed to in writing, software
# distributed under the License is distributed on an "AS IS" BASIS,
# WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
# See the License for the specific language governing permissions and
# limitations under the License.

#TODO pass parameters for version and for path

GORA_HOME="$1"
GORA_VERSION="$2"
echo ". . . Installing GORA-CORE . . ."
mvn install:install-file -Dfile=$GORA_HOME/gora-core/target/gora-core-$GORA_VERSION.jar -DgroupId=org.apache.gora -DartifactId=gora-core -Dversion=$GORA_VERSION -Dpackaging=jar -DgeneratePom=true
echo ". . . Installing GORA-CASSANDRA . . ." 
mvn install:install-file -Dfile=$GORA_HOME/gora-cassandra/target/gora-cassandra-$GORA_VERSION.jar -DgroupId=org.apache.gora -DartifactId=gora-cassandra -Dversion=$GORA_VERSION -Dpackaging=jar -DgeneratePom=true
echo ". . . Installing GORA-DYNAMODB . . ."
mvn install:install-file -Dfile=$GORA_HOME/gora-dynamodb/target/gora-dynamodb-$GORA_VERSION.jar -DgroupId=org.apache.gora -DartifactId=gora-dynamodb -Dversion=$GORA_VERSION -Dpackaging=jar -DgeneratePom=true
echo ". . . Installing GORA-HBASE . . ."
mvn install:install-file -Dfile=$GORA_HOME/gora-hbase/target/gora-hbase-$GORA_VERSION.jar -DgroupId=org.apache.gora -DartifactId=gora-hbase -Dversion=$GORA_VERSION -Dpackaging=jar -DgeneratePom=true

#install.mvn.local.deps.sh /Users/renatomarroquin/Documents/workspace/workspaceGoraBranches/gora-rmm 0.5-SNAPSHOT