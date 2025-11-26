<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">

    <xsl:key name="cityKey" match="auto" use="tulaj/varos"/>

    <xsl:template match="/">
        <html>
            <head>
                <title>Városonkénti autók</title>
            </head>
            <body>
                <h2>Városonkénti darabszám és összár</h2>
                <table border="2">
                    <tr>
                        <th>Város</th>
                        <th>Darabszám</th>
                        <th>Összár</th>
                    </tr>
                    <xsl:for-each select="autok/auto[generate-id() = generate-id(key('cityKey', tulaj/varos)[1])]">
                        <tr>
                            <td><xsl:value-of select="tulaj/varos" /></td>
                            <td><xsl:value-of select="count(key('cityKey', tulaj/varos))" /></td>
                            <td>
                                <xsl:value-of select="sum(key('cityKey', tulaj/varos)/ar)" />
                            </td>
                        </tr>
                    </xsl:for-each>
                </table>
            </body>
        </html>
    </xsl:template>

    <xsl:output method="html" indent="yes" encoding="UTF-8"/>

</xsl:stylesheet>
