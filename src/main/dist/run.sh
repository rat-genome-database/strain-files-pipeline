#!/usr/bin/env bash
#
# Strain Files pipeline
#
. /etc/profile
APPNAME=strain-files-pipeline
APPDIR=/home/rgddata/pipelines/$APPNAME
SERVER=`hostname -s | tr '[a-z]' '[A-Z]'`

EMAILLIST="llamers@mcw.edu mtutaj@mcw.edu"
if [ "$SERVER" == "REED" ]; then
  EMAILLIST="llamers@mcw.edu mtutaj@mcw.edu jrsmith@mcw.edu sjwang@mcw.edu"
fi

cd $APPDIR

java -Dspring.config=$APPDIR/../properties/default_db2.xml \
    -Dlog4j.configurationFile=file://$APPDIR/properties/log4j2.xml \
    -jar lib/$APPNAME.jar "$@" > run.log 2>&1

mailx -s "[$SERVER] Strain Files Pipeline Run" $EMAILLIST < $APPDIR/logs/summary.log
