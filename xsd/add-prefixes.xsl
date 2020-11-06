<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.0"
    xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
    xmlns:xs="http://fr.albinscode/1/main-with-import">

<!--     <xsl:output method="xml" indent="yes"/> -->

    <!-- We copy all elements -->
    <xsl:template match="node()|@*">
        <xsl:copy>
            <xsl:apply-templates select="node()|@*"/>
        </xsl:copy>
    </xsl:template>
    
    <!-- we add namespace to all book direct childrens-->
    <xsl:template match="//xs:book/*">
        <xsl:element name="commons:{local-name()}" namespace="http://fr.albinscode/1/commons">
            <xsl:copy-of select="@*" />
            <xsl:apply-templates />
        </xsl:element>
    </xsl:template>

</xsl:stylesheet>
