<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">

    <xsl:template match="/">
        <html>
            <head>
                <title>30000 Ft-nál drágább autók</title>
            </head>
            <body>
                <h2>30000 Ft feletti autók darabszáma</h2>
                <p>
                    Darabszám:
                    <b><xsl:value-of select="count(autok/auto[ar &gt; 30000])"/></b>
                </p>
            </body>
        </html>
    </xsl:template>

    <xsl:output method="html" indent="yes" encoding="UTF-8"/>

</xsl:stylesheet>
