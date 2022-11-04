1. 分词的目的是为了索引。不作分词处理字段：商品id、订单号、身份证号等
2. 索引的目的是为了搜索，不索引则该域的内容无法搜索到，商品id、文件路径等，不用作为查询条件的不用索引
3. 存储的才能从Document中获取到
#### lucene常见Field

|名称|数据类型|分词|索引|描述|
|---|---|---|---|---|
|IntPoint|int|-|-|需要注意的是如果需要对InfField进行排序使用SortField.Type.INT来比较，如果进范围查询或过滤，需要采用NumericRangeQuery.newIntRange()
|LongPoint|long|Y|Y|排序使用SortField.Type.Long,如果进行范围查询或过滤利用NumericRangeQuery.newLongRange()，LongField常用来进行时间戳的排序，保存System.currentTimeMillions()
|FloatField|Float|-|-|排序采用SortField.Type.Float,范围查询采用NumericRangeQuery.newFloatRange()
|StringField|String|N|Y
|TextField|String|Y|Y|
|StoredField|-|-|-|用来存储数据|