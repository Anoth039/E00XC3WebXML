<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">

    <xsl:template match="/">
        <html>
            <head>
                <title>Autók ár szerint</title>
            </head>
            <body>
                <h2>Autók rendszáma és ára - ár szerint rendezve</h2>
                <ul>
                    <xsl:for-each select="autok/auto">
                        <xsl:sort select="ar" data-type="number" order="ascending"/>
                        <li>
                            <xsl:value-of select="@rsz"/> -
                            <xsl:value-of select="ar"/> Ft
                        </li>
                    </xsl:for-each>
                </ul>
            </body>
        </html>
    </xsl:template>

    <xsl:output method="html" indent="yes" encoding="UTF-8"/>

</xsl:stylesheet>
