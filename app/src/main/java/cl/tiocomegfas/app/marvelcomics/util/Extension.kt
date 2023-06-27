package cl.tiocomegfas.app.marvelcomics.util

fun String.getIdByURI(): Int? {
    val regex = Regex("""/(\d+)$""")
    val matchResult = regex.find(this)
    return matchResult?.groupValues?.lastOrNull()?.toIntOrNull()
}