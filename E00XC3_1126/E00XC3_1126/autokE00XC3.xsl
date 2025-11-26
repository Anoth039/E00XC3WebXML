<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">

    <xsl:template match="/">
        <html>
            <head>
                <title>Autók rendszámai</title>
            </head>
            <body>
                <h2>Autók rendszámai</h2>
                <ul>
                    <xsl:for-each select="autok/auto">
                        <li><xsl:value-of select="@rsz" /></li>
                    </xsl:for-each>
                </ul>
            </body>
        </html>
    </xsl:template>

    <xsl:output method="html" indent="yes" encoding="UTF-8"/>

</xsl:stylesheet>