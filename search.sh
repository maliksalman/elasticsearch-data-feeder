JSON='{
  "track_total_hits": true,
  "size": 2,
  "query": {
    "bool": {
      "must": [
        { "term": { "attributes.covfefe": "semper" } }
      ]
    }
  }
}'

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
#        { "term": { "attributes.nii": "scripserit" } }
#      ]
#    }
#  }
#}'

#JSON='{
#  "track_total_hits": true,
#  "size": 5,
#  "query": {
#    "bool": {
#      "must": [
#        { "range": { "createdAt": { "gte": "2020-12-06", "lt": "2021-01-08" } } }
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
#        { "prefix": { "data": "se" } }
#      ]
#    }
#  },
#  "aggs": {
#    "covfefe_values": {
#      "terms": { "size": 5, "field": "attributes.covfefe" }
#    },
#    "stooge_values": {
#      "terms": { "size": 5, "field": "attributes.stooge" }
#    }
#  }
#}'

curl -s -X GET "localhost:9200/my-data/_search" -H 'Content-Type: application/json' -d"$JSON" | jq .
