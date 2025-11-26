<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">

    <xsl:template match="/">
        <html>
            <head>
                <title>Dokumentum elemszáma</title>
            </head>
            <body>
                <h2>XML dokumentum elemszáma</h2>
                <p>
                    Összes elem száma:
                    <b><xsl:value-of select="count(//*)"/></b>
                </p>
            </body>
        </html>
    </xsl:template>

    <xsl:output method="html" indent="yes" encoding="UTF-8"/>

</xsl:stylesheet>
