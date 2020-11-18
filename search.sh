#JSON='{
#  "track_total_hits": true,
#  "size": 2,
#  "query": {
#    "bool": {
#      "must": [
#        { "term": { "attributes.covfefe": "semper" } }
#      ]
#    }
#  }
#}'

#JSON='{
#  "track_total_hits": true,
#  "size": 2,
#  "query": {
#    "bool": {
#      "must": [
#        { "terms": { "attributes.covfefe": [ "semper", "pri" ] } }
#      ]
#    }
#  }
#}'

#JSON='{
#  "track_total_hits": true,
#  "size": 2,
#  "query": {
#    "bool": {
#      "must": [
#        { "term": { "attributes.covfefe": "omittantur" } },
#        { "term": { "attributes.bla": "scripserit" } }
#      ]
#    }
#  }
#}'

#JSON='{
#  "track_total_hits": true,
#  "size": 2,
#  "query": {
#    "bool": {
#      "must": [
#        { "range": { "startDate": { "gte": "2020-11-06", "lt": "2020-11-08" } } }
#      ]
#    }
#  }
#}'

JSON='{
  "track_total_hits": true,
  "size": 2,
  "query": {
    "bool": {
      "must": [
        { "prefix": { "attributes.covfefe": "se" } }
      ]
    }
  },
  "aggs": {
    "covfefe_values": {
      "terms": { "size": 5, "field": "attributes.covfefe" }
    },
    "stooge_values": {
      "terms": { "size": 5, "field": "attributes.stooge" }
    }
  }
}'

curl -s -X GET "localhost:9200/my-index/_search" -H 'Content-Type: application/json' -d"$JSON" | jq .
