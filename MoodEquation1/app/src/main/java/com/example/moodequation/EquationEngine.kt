package com.moodequation

data class EquationResult(val phrase: String, val color: String)

object EquationEngine {

    private val pairResults = mapOf(
        "tender+hollow"    to EquationResult("Grief you have named a pet",               "#c080a0"),
        "tender+electric"  to EquationResult("Static cling of love",                     "#d890b0"),
        "tender+heavy"     to EquationResult("Carrying someone else's sorrow",           "#a06880"),
        "tender+golden"    to EquationResult("Memory of a hand in yours",                "#d8a078"),
        "tender+still"     to EquationResult("The quiet after crying",                   "#90b0a0"),
        "tender+longing"   to EquationResult("Distance felt in the chest",               "#9080b8"),
        "tender+sharp"     to EquationResult("The truth that still hurts gently",        "#b090b0"),
        "tender+warm"      to EquationResult("Being held without asking",                "#c89080"),
        "tender+soft"      to EquationResult("Love you do not say out loud",             "#b090c0"),
        "hollow+electric"  to EquationResult("Anxiety dressed as excitement",            "#9898c0"),
        "hollow+sharp"     to EquationResult("Clarity with no comfort",                  "#9090b8"),
        "hollow+longing"   to EquationResult("Homesick for a place you invented",        "#7080a8"),
        "hollow+heavy"     to EquationResult("The morning after a loss",                 "#707090"),
        "hollow+hazy"      to EquationResult("Forgetting why you walked in here",        "#909090"),
        "hollow+soft"      to EquationResult("Sleep when nothing resolves",              "#8888a8"),
        "electric+bold"    to EquationResult("Courage about to make a mistake",          "#e09040"),
        "electric+sharp"   to EquationResult("Hyper-focus at 2am",                      "#d0c060"),
        "electric+warm"    to EquationResult("The start of something you cannot name",   "#e09860"),
        "electric+longing" to EquationResult("Inspiration you cannot reach fast enough", "#9090c8"),
        "heavy+bold"       to EquationResult("Determination after a long fall",          "#a04040"),
        "heavy+longing"    to EquationResult("Missing a version of yourself",            "#6878a0"),
        "heavy+warm"       to EquationResult("Comfort that costs you something",         "#907060"),
        "golden+still"     to EquationResult("Afternoon with nowhere to be",             "#c0a870"),
        "golden+warm"      to EquationResult("Nostalgia you can almost smell",           "#e09858"),
        "golden+soft"      to EquationResult("The end of something beautiful",           "#c098a8"),
        "golden+longing"   to EquationResult("Wishing you could go back just once",      "#c0a890"),
        "still+soft"       to EquationResult("The peace before a dream",                 "#8090b8"),
        "still+hazy"       to EquationResult("Sunday without an agenda",                 "#909898"),
        "still+longing"    to EquationResult("Waiting without knowing what for",         "#8090a0"),
        "sharp+bold"       to EquationResult("Righteousness, unasked for",               "#c06060"),
        "sharp+hazy"       to EquationResult("A memory that might be a dream",           "#a0a0b0"),
        "warm+soft"        to EquationResult("Being known without explaining",            "#c08898"),
        "longing+soft"     to EquationResult("Every song that reminds you of them",      "#8080c0"),
        "hazy+soft"        to EquationResult("Forgetting something important",            "#9898b8"),
        "bold+warm"        to EquationResult("Saying exactly what you mean",             "#c06040")
    )

    private val fallbacks = listOf(
        "An emotion without a name yet",
        "Something between two feelings",
        "A feeling you carry quietly",
        "The weight of almost"
    )

    fun compute(selected: List<Mood>): EquationResult {
        val names = selected.map { it.name }.sorted()
        for (i in names.indices)
            for (j in i + 1 until names.size) {
                val key = "${names[i]}+${names[j]}"
                pairResults[key]?.let { return it }
            }
        return EquationResult(fallbacks.random(), blendHexColors(selected.map { it.accentColor }))
    }

    private fun blendHexColors(hexColors: List<String>): String {
        var r = 0; var g = 0; var b = 0
        hexColors.forEach {
            val c = it.trimStart('#')
            r += c.substring(0,2).toInt(16); g += c.substring(2,4).toInt(16); b += c.substring(4,6).toInt(16)
        }
        val n = hexColors.size
        return "#%02x%02x%02x".format(r/n, g/n, b/n)
    }
}