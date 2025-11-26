<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">

    <xsl:template match="/">
        <html>
            <head>
                <title>Miskolci autók</title>
            </head>
            <body>
                <h2>Miskolci tulajdonosok autóinak rendszámai</h2>
                <ul>
                    <xsl:for-each select="autok/auto[tulaj/varos='Miskolc']">
                        <li><xsl:value-of select="@rsz"/></li>
                    </xsl:for-each>
                </ul>
            </body>
        </html>
    </xsl:template>

    <xsl:output method="html" indent="yes" encoding="UTF-8"/>

</xsl:stylesheet>
