# orchestra2json

Process to generate JSON schemas for message structures defined in a FIX Orchestra file.


## XSL Transforms

### AddJsonDatatypes.xslt
Adds JSON mappings for FIX datatypes to an Orchestra file. The mappings are used in schema generation.

### orchestra2json.xslt
Generates JSON schema files for messages, components, and repeating groups defined in an Orchestra file.

## References

The Orchestra XML schema is in project [fix-orchestra/repository2016](https://github.com/FIXTradingCommunity/fix-orchestra/tree/master/repository2016)

[JSON Schema](http://json-schema.org/documentation.html)
