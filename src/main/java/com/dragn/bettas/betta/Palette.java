package com.dragn.bettas.betta;

import com.dragn.bettas.BettasMain;

public enum Palette {
    BLUES(
            new int[]{0xffdc0004, 0xddc81b04, 0xdda63504, 0xdd81351f, 0xff58351f, 0xff952215, 0xff7a3a15, 0xffca7615, 0xffaf6c15, 0xff946200},
            new int[]{0xffc80004, 0xdda61b04, 0xdd813504, 0xdd62351f, 0xff3d2214, 0xff7a2215, 0xff532e15, 0xffaf6c15, 0xffaf8415, 0xff765b00}
    ),
    REDS(
            new int[]{0xcc1b00f1, 0xcc000079, 0xcc001dd8, 0xcc161dbe, 0xcc161dff, 0xcc0000ff, 0xcc00005f, 0xff0000cf, 0xff0000ed, 0xff000061},
            new int[]{0xcc0000c9, 0xcc000068, 0xcc001dd8, 0xcc161da3, 0xcc161dc9, 0xcc0000c9, 0xcc000036, 0xff0000ad, 0xff0000cf, 0xff00004b}
    ),
    PINKS(
            new int[]{0xcc9128eb, 0xccb47cd6, 0xcc915eff, 0xcc7d27dc, 0xcc885bd0},
            new int[]{0xcc5417bd, 0xcc915ec2, 0xcc7d5ec2, 0xcc6127b4, 0xcc724aac}
    ),
    GREENS(
            new int[]{0xff6cb700, 0xffa0d200, 0xff5ab700, 0xff49b84f, 0xff67b38a, 0xff9dcc6f, 0xcc91ff54, 0xff82c354, 0xff71b400, 0xff7fb300},
            new int[]{0xff589c00, 0xff89b700, 0xff49a500, 0xff3c9c38, 0xff55996f, 0xff88bc6f, 0xff82d254, 0xff71ad54, 0xff63a200, 0xff679500}
    ),
    ORANGES(
            new int[]{0xff008cff, 0xff005dff, 0xff003fbb, 0xff557dff, 0xff002785, 0xff005298, 0xff0047e6, 0xff0046bc, 0xff0085ff, 0xff001d82},
            new int[]{0xff007ae3, 0xff005dd9, 0xff003d9b, 0xff556ce6, 0xff001d6f, 0xff003884, 0xff002c76, 0xff003db1, 0xff0067ce, 0xff001370}
    ),
    PASTELS(
            new int[]{0x6000d5ff, 0x84ffd5ff, 0x3fe24700, 0x4979ff00, 0x510033ff, 0x3f0000ff, 0x3f0050ff, 0x28008e5c, 0x210000ff, 0x1eff6db7},
            new int[]{0x8400d5ff, 0xb5e2c1e2, 0x66e24700, 0x6679ff00, 0x6d0033ff, 0x630000ff, 0x5e0050ff, 0x49008e5c, 0x2d0000ff, 0x33ff6db7}
    ),
    PURPLES(
            new int[]{0xb2430045, 0x66430045, 0xd8430038, 0xe5430060},
            new int[]{0xcc430045, 0x8c430045, 0xe5430038, 0xf2390054}
    ),
    GREYS(
            new int[]{0xff363637, 0xffecedeb, 0xd3000000, 0xa5000000, 0xa000000},
            new int[]{0xff202223, 0xffcfcfce, 0xe5000000, 0xc6000000, 0x23000000}
    );

    private final int[] colors;
    private final int[] shades;

    Palette(int[] colors, int[] shades) {
        this.colors = colors;
        this.shades = shades;
    }

    public static Palette getRandomPalette() {
        return Palette.values()[BettasMain.RANDOM.nextInt(Palette.values().length)];
    }

    public int getRandomColor() {
        return colors[BettasMain.RANDOM.nextInt(colors.length)];
    }

    public int getRandomShade() {
        return colors[BettasMain.RANDOM.nextInt(shades.length)];
    }
}