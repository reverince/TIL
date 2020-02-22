CHO = [?ㄱ,?ㄲ,?ㄴ,?ㄷ,?ㄸ,?ㄹ,?ㅁ,?ㅂ,?ㅃ,?ㅅ,?ㅆ,?ㅇ,?ㅈ,?ㅉ,?ㅊ,?ㅋ,?ㅌ,?ㅍ,?ㅎ]
JONG = ["",?ㄱ,?ㄲ,?ㄳ,?ㄴ,?ㄵ,?ㄶ,?ㄷ,?ㄹ,?ㄺ,?ㄻ,?ㄼ,?ㄽ,?ㄾ,?ㄿ,?ㅀ,?ㅁ,?ㅂ,?ㅄ,?ㅅ,?ㅆ,?ㅇ,?ㅈ,?ㅊ,?ㅋ,?ㅌ,?ㅍ,?ㅎ]
HANGUL_BASE = 44032
JUNG_BASE = 12623

def separate_hangul
  offset = gets.unpack(?U)[0] - HANGUL_BASE
  jong = offset % 28
  chojung = offset - jong
  jung_offset = chojung / 28 % 21
  cho = chojung / 28 / 21

  puts CHO[cho], [JUNG_BASE+jung_offset].pack(?U)[0], JONG[jong]
end

def combine_hangul
  cho_ch = gets.unpack(?U).pack(?U)
  jung_ch = gets.unpack(?U).pack(?U)
  jong_ch = gets.unpack(?U).pack(?U)

  cho = CHO.index(cho_ch)
  jung = jung_ch.unpack(?U)[0] - JUNG_BASE
  jong = JONG.index(jong_ch)
  jong ||= 0

  code = HANGUL_BASE + (cho * 21 + jung) * 28 + jong
  puts [code].pack(?U)
end
