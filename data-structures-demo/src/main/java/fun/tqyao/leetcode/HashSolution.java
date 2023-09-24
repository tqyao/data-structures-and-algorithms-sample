package fun.tqyao.leetcode;

import org.junit.Test;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class HashSolution {

    /**
     * 句子中出现最多单词
     * paragraph=[Bob hit a ball, the hit BALL flew far after it was hit.]
     * banned=[hit]
     * 返回 ball
     *
     * @param paragraph
     * @param banned
     * @return java.lang.String
     * @author tqyao
     * @create 2023/9/24 09:09
     */
    public String mostCommonWord100(String paragraph, String[] banned) {
        System.out.println(paragraph);
//        String s = paragraph.toLowerCase().replaceAll(",|\\.", "");
//        String[] s1 = s.split(" ");
        String[] s1 = paragraph.toLowerCase().split("[^A-Za-z]+");
//        System.out.println(Arrays.toString(s1));
        Map<String, Integer> map = new HashMap<>();
        // 是否存在与禁用词列表
        boolean flag = false;
        // 遍历每个单词放入 hashmap 中。【单词，频次】
        for (String s2 : s1) {
            // 单词是否是禁用词。不是才加入 hashmap
            for (String s3 : banned) {
                if (s3.equals(s2)) {
                    flag = true;
                    break;
                }
            }
            if (!flag) {
                map.put(s2, map.getOrDefault(s2, 0) + 1);
            }
            flag = false;
        }

        // 查找 map.value 中频次最大值
        Collection<Integer> values = map.values();
        Iterator<Integer> iterator = values.iterator();
        int max = Integer.MIN_VALUE;
        while (iterator.hasNext()) {
            max = Math.max(iterator.next(), max);
        }

        // 如果每个单词出现次数一样则返回空串
        if (max == 1) {
            return "";
        }

        // 查找出现频次最多的 hash.value 中对应的 key
        for (Map.Entry<String, Integer> entry : map.entrySet()) {
            if (entry.getValue().equals(max)) {
                return entry.getKey();
            }
        }

        return "";
    }

    public String mostCommonWord101(String paragraph, String[] banned) {
        System.out.println(paragraph);
        String[] s1 = paragraph.toLowerCase().split("[^A-Za-z]+");
//        System.out.println(Arrays.toString(s1));
        Map<String, Integer> map = new HashMap<>();
        Set<String> collect = Stream.of(banned).collect(Collectors.toSet());
        // 计算每一个单词的出现频次放入 hashmap [单词：频次]
        for (String s : s1) {
            if (!collect.contains(s)) {
                map.compute(s, (k, v) -> v == null ? 1 : v + 1);
            }
        }

        //查找最大
//        Optional<Map.Entry<String, Integer>> maxEntryOpl = map.entrySet().stream().max((c1, c2) -> c1.getValue().compareTo(c2.getValue()));
        Optional<Map.Entry<String, Integer>> maxEntryOpl = map.entrySet().stream().max(Map.Entry.comparingByValue());
//        System.out.println(maxEntryOpl);
        return maxEntryOpl.map(Map.Entry::getKey).orElse(null);
    }

    public String mostCommonWord102(String paragraph, String[] banned) {
        Set<String> collect = Stream.of(banned).collect(Collectors.toSet());
        char[] chars = paragraph.toLowerCase().toCharArray();

        // 拼接单词，并放入 map
        StringBuilder sb = new StringBuilder();
        Map<String, Integer> map = new HashMap<>();
        for (char ch : chars) {
            if (ch <= 'z' && ch >= 'a') {
                // 非特殊字符，加入 sb
                sb.append(ch);
            } else {
                // 遇到特殊字符，说明已经是完整单词
                String s = sb.toString();
//                System.out.println(s);
                if (!collect.contains(s)) {// 过滤禁用词 banned
                    map.compute(s, (k, v) -> v == null ? 1 : v + 1);
                }
                // 清空 sb，准备拼接下一个单词
                sb.setLength(0);
            }
        }

        // 处理只有一个单词情况
        if (sb.length() != 0) {
            String s = sb.toString();
            if (!collect.contains(s)) {// 过滤禁用词 banned
                map.compute(s, (k, v) -> v == null ? 1 : v + 1);
            }
        }



        // 从 map 中查找出现频次最多单词
        int max = 0;
        String maxStr = null;
        for (Map.Entry<String, Integer> entry : map.entrySet()) {
            if (entry.getValue() > max) {
                max = entry.getValue();
                maxStr = entry.getKey();
            }
        }

        return maxStr;
    }


    @Test
    public void testMostCommonWord() {
//        System.out.println((int) 'a');
//        System.out.println(mostCommonWord102("Bob hit a ball, the hit BALL flew far after it was hit.", new String[]{"hit"}));
        System.out.println(mostCommonWord102("Bob", new String[]{"hit"}));

//        System.out.println(mostCommonWord("Bob hit a ball, the hit BALL flew far after it was hit.", new String[]{"hit"}));
//        System.out.println(mostCommonWord("it was hit.", new String[]{"hit"}));

//        Map<String, Integer> map = new HashMap<>();
//        map.put("asd", 123);
//        List<Integer> values = (List<Integer>) map.values();
//        System.out.println(values.get(0));
//        Collection<Integer> values = map.values();

    }

    /**
     * https://leetcode.cn/problems/first-unique-character-in-a-string/
     *
     * @param s
     * @return int
     * @author tqyao
     * @create 2023/9/22 11:04
     */
    public int firstUniqChar(String s) {
        return -1;
    }

    /**
     * 1. 两次遍历，hash 表存储字符频次
     * 2. 第二次遍历字符串字符，在 hash 表中查找，如果字符频率为 1 则返回索引
     *
     * @param s
     * @return int
     * @author tqyao
     * @create 2023/9/22 11:20
     */
    public int firstUniqChar100(String s) {
        HashMap<Character, Integer> hashMap = new HashMap<>();
        for (int i = 0; i < s.length(); i++) {
            char ch = s.charAt(i);
            hashMap.put(ch, hashMap.getOrDefault(ch, 0) + 1);
        }
        for (int i = 0; i < s.length(); i++) {
            char ch = s.charAt(i);
            if (hashMap.get(ch) == 1) {
                return i;
            }
        }
        return -1;
    }

    /**
     * 存储索引
     *
     * @param s
     * @return int
     * @author tqyao
     * @create 2023/9/22 11:22
     */
    public int firstUniqChar200(String s) {
        HashMap<Character, Integer[]> hashMap = new HashMap<>();
        for (int i = 0; i < s.length(); i++) {
            char ch = s.charAt(i);
            Integer[] idxs = hashMap.get(ch);
            if (idxs == null) {
                idxs = new Integer[]{i, -1};
            } else {
                idxs[1] = i;
            }
            hashMap.put(ch, idxs);
        }

        for (int i = 0; i < s.length(); i++) {
            char ch = s.charAt(i);
            Integer[] repeats = hashMap.get(ch);
            if (repeats[1] == -1) {
                return i;
            }
        }
        return -1;
    }

    public int firstUniqChar201(String s) {
        //【字符，索引】
        HashMap<Character, Integer> hashMap = new HashMap<>();
        for (int i = 0; i < s.length(); i++) {
            char ch = s.charAt(i);
            Integer integer = hashMap.get(ch);
            if (integer == null) {
                integer = i;
            } else {
                integer = -1;
            }
            hashMap.put(ch, integer);
        }

        for (int i = 0; i < s.length(); i++) {
            char ch = s.charAt(i);
            if (hashMap.get(ch) != -1) {
                return hashMap.get(ch);
            }
        }
        return -1;
    }

    /**
     * todo 队列
     *
     * @param s
     * @return int
     * @author tqyao
     * @create 2023/9/22 12:15
     */
    public int firstUniqChar300(String s) {
        return 0;
    }

    @Test
    public void testfirstUniqChar200() {

        System.out.println(firstUniqChar200("leetcode"));
//        HashMap<Character, Integer[]> hashMap = new HashMap<>();
//        Integer[] as = hashMap.put('a', new Integer[]{1});
//        System.out.println(Arrays.toString(as));
//        Integer[] as1 = hashMap.put('a', new Integer[]{1, 2});
//        System.out.println(Arrays.toString(as1));

    }


    /**
     * https://leetcode.cn/problems/dKk3P7/
     *
     * @param s
     * @param t
     * @return boolean
     * @author tqyao
     * @create 2023/9/22 09:22
     */
    public boolean isAnagram(String s, String t) {
        if (s.length() != t.length() || s.equals(t)) return false;

        // 分别吧两字符串放入哈希表。key=字符 value=出现次数
        HashMap<Character, Integer> hashMap1 = new HashMap<>();
        HashMap<Character, Integer> hashMap2 = new HashMap<>();
        for (int i = 0; i < s.length(); i++) {
            char ch1 = s.charAt(i);
            char ch2 = t.charAt(i);
            Integer orDefault1 = hashMap1.getOrDefault(ch1, 0);
            hashMap1.put(ch1, ++orDefault1);
            Integer orDefault2 = hashMap2.getOrDefault(ch2, 0);
            hashMap2.put(ch2, ++orDefault2);
        }

        // 遍历一次，比较存在与哈希表的每个元素键值是否相等。不相等则不是异位词
        for (Map.Entry<Character, Integer> entry : hashMap1.entrySet()) {
            if (!entry.getValue().equals(hashMap2.get(entry.getKey()))) {
                return false;
            }
        }

        return true;
    }


    /**
     * 1. 字符 1 映射到 hash表 【key=字符，value=出现频次】
     * 2. 用字符 2 每个字符在 hash 表中查找做差
     * 3. 如果出现某个key 对应的频次小于 0 说明字符 1，2 不满足异位词条件
     *
     * @param s
     * @param t
     * @return boolean
     * @author tqyao
     * @create 2023/9/22 10:48
     */
    public boolean isAnagram100(String s, String t) {
        if (s.length() != t.length() || s.equals(t)) return false;
        // 【key=字符，value=出现频次】
        HashMap<Character, Integer> hashMap = new HashMap<>();

        for (int i = 0; i < s.length(); i++) {
            char ch = s.charAt(i);
            hashMap.put(ch, hashMap.getOrDefault(ch, 0) + 1);
        }

        for (int i = 0; i < t.length(); i++) {
            char ch = t.charAt(i);
            hashMap.put(ch, hashMap.getOrDefault(ch, 0) - 1);
            if (hashMap.get(ch) < 0) {
                return false;
            }
        }

        return true;
    }

    /**
     * 1. 把两字符映射到 长度为 26 的数组，数组的 key 为字符 ascii-'a'
     * 2. 依次比较两数组中s 串字符出现频次
     *
     * @param s
     * @param t
     * @return boolean
     * @author tqyao
     * @create 2023/9/22 10:30
     */
    public boolean isAnagram102(String s, String t) {
        if (s.length() != t.length() || s.equals(t)) return false;

        int[] table = new int[26];
        int[] table2 = new int[26];
        for (int i = 0; i < s.length(); i++) {
            table[s.charAt(i) - 'a']++;
            table2[t.charAt(i) - 'a']++;
        }
        for (int i = 0; i < s.length(); i++) {
            char ch = s.charAt(i);
            if (table[ch - 'a'] - table2[ch - 'a'] != 0) {
                return false;
            }
        }

        return true;
    }

    /**
     * 排序
     * rat
     * car
     *
     * @param s
     * @param t
     * @return boolean
     * @author tqyao
     * @create 2023/9/22 10:54
     */
    public boolean isAnagram200(String s, String t) {
        if (s.length() != t.length() || s.equals(t)) return false;
        char[] ch1s = s.toCharArray();
        char[] ch2s = t.toCharArray();
        Arrays.sort(ch1s);
        Arrays.sort(ch2s);

        for (int i = 0; i < ch1s.length; i++) {
            if (ch1s[i] != ch2s[i]) {
                return false;
            }
        }
        return true;
    }


    @Test
    public void testIsAnagram() {

//        int[] a = new int[2];
//        a[0]++;
//
//        System.out.println(Arrays.toString(a));

//        System.out.println(isAnagram100("anagram", "nagaram"));
        System.out.println(isAnagram100("rat", "car"));
//        HashMap<Character, Integer> hashMap1 = new HashMap<>();
//        HashMap<Character, Integer> hashMap2 = new HashMap<>();
//        hashMap1.put('1', 2);
//        hashMap2.put('1', 2);
//        System.out.println(hashMap1.containsKey(hashMap2));
    }


    /**
     * https://leetcode.cn/problems/single-number/
     *
     * @param nums
     * @return int
     * @author tqyao
     * @create 2023/9/21 19:39
     */
    public int singleNumber(int[] nums) {
        Map<Integer, Integer> map = new HashMap<>();
        for (int num : nums) {
            // (num：出现次数)
            Integer orDefault = map.getOrDefault(num, 0);
            map.put(num, ++orDefault);
        }

        Set<Map.Entry<Integer, Integer>> entries = map.entrySet();

        for (Map.Entry<Integer, Integer> entry : entries) {
            if (entry.getValue() == 1) {
                return entry.getKey();
            }
        }
        return 0;
    }

    // set
    public int singleNumber101(int[] nums) {
        Set<Integer> set = new HashSet<>();
        for (int num : nums) {
            if (!set.add(num)) {
                set.remove(num);
            }
        }
        return set.stream().mapToInt(n -> n).toArray()[0];
    }


    /**
     * 任何数和 000 做异或运算，结果仍然是原来的数，即 a⊕0=aa \oplus 0=aa⊕0=a。
     * 任何数和其自身做异或运算，结果是 000，即 a⊕a=0a \oplus a=0a⊕a=0。
     * ⭐异或运算满足交换律和结合律，即 a⊕b⊕a=b⊕a⊕a=b⊕(a⊕a)=b⊕0=ba \oplus b \oplus a=b \oplus a \oplus a=b \oplus (a \oplus a)=b \oplus0=ba⊕b⊕a=b⊕a⊕a=b⊕(a⊕a)=b⊕0=b。
     * 所以 5 ^ 2 ^ 4 ^ 5 ^ 2  = 4
     * 作者：力扣官方题解
     * 链接：https://leetcode.cn/problems/single-number/solutions/242211/zhi-chu-xian-yi-ci-de-shu-zi-by-leetcode-solution/
     * 来源：力扣（LeetCode）
     * 著作权归作者所有。商业转载请联系作者获得授权，非商业转载请注明出处。
     *
     * @param nums
     * @return int
     * @author tqyao
     * @create 2023/9/21 20:14
     */
    public int singleNumber201(int[] nums) {
        int a = nums[0];
        for (int i = 0; i < nums.length - 1; i++) {
            a = a ^ nums[i + 1];
        }
        return a;
    }


    // 排序
    public int singleNumber300(int[] nums) {
        Arrays.sort(nums);
        Set<Integer> set = new HashSet<>();
        if (nums.length == 1) {
            return nums[0];
        }
        for (int i = 0; i < nums.length - 1; i++) {
            if (set.contains(nums[i]) || nums[i] == nums[i + 1]) {
                set.add(nums[i]);
                continue;
            }
            return nums[i];
        }
        return nums[nums.length - 1];
    }


    // 暴力
    public int singleNumber301(int[] nums) {
        boolean flag = false;
        Set<Integer> set = new HashSet<>();
        for (int i = 0; i < nums.length; i++) {
            for (int j = i; j < nums.length - 1; j++) {
                if (set.contains(nums[i]) || nums[i] == nums[j + 1]) {
                    flag = true;
                    set.add(nums[i]);
                    break;
                }
            }
            if (!flag) {
                return nums[i];
            }
            flag = false;
        }
        return 0;
    }


    public int singleNumber302(int[] nums) {
        boolean flag = false;
        Set<Integer> set = new HashSet<>();
        for (int i = 0; i < nums.length; i++) {
            for (int j = i; j < nums.length - 1; j++) {
                if (set.contains(nums[i]) || nums[i] == nums[j + 1]) {
                    // 遇到重复元素
                    flag = true;
                    set.add(nums[i]);
                }
            }
            if (!flag) {
                // 循环一遍后未遇到重复元素
                return nums[i];
            }
            flag = false;
        }
        return 0;
    }

    @Test
    public void test0() {
        int[] ints = Stream.of(4, 1, 2, 1, 2).mapToInt(Integer::new).toArray();
//        int[] ints = Stream.of(4, 4, 1).mapToInt(Integer::new).toArray();
        System.out.println(singleNumber300(ints));

//        singleNumber(ints);
//        System.out.println(5 ^ 2 ^ 4 ^ 5 ^ 2);
    }

    /**
     * https://leetcode.cn/problems/contains-duplicate/description/
     *
     * @param nums
     * @return boolean
     * @author tqyao
     * @create 2023/9/17 10:31
     */
    public boolean containsDuplicate(int[] nums) {
        Set<Integer> set = new HashSet<>();
        for (int num : nums) {
            set.add(num);
        }
        return set.size() != nums.length;
    }


    /**
     * 排序
     * [1,2,3,4,5]
     *
     * @param nums
     * @return boolean
     * @author tqyao
     * @create 2023/9/17 10:48
     */
    public boolean containsDuplicate200(int[] nums) {
        Arrays.sort(nums);
        for (int i = 0; i < nums.length - 1; i++) {
            if (nums[i] == nums[i + 1]) {
                return true;
            }
        }
        return false;
    }

    public boolean containsDuplicate101(int[] nums) {
        HashMap<Integer, Integer> hashMap = new HashMap<>();
        for (int num : nums) {
            Integer orDefault = hashMap.getOrDefault(num, 1);
            if (orDefault == 2) {
                return true;
            }
            hashMap.put(num, 2);

//            if (hashMap.get(num) != null) {
//                return true;
//            }
//            hashMap.put(num, 1);
        }
        return false;
    }

    /**
     * 输入: strs = ["eat", "tea", "tan", "ate", "nat", "bat"]
     * 输出: [["bat"],["nat","tan"],["ate","eat","tea"]]
     * <p>
     * 变位词：
     * <p>
     * https://leetcode.cn/problems/sfvd7V/
     *
     * @param strs
     * @return java.util.List<java.util.List < java.lang.String>>
     * @author tqyao
     * @create 2023/9/16 20:16
     */
    public List<List<String>> groupAnagrams100(String[] strs) {
        HashMap<String, List<String>> hashtable = new HashMap<>();

        for (int i = 0; i < strs.length; i++) {
//            String sortStr = Arrays.stream(strs[i].split("")).sorted().collect(Collectors.joining());
            char[] chars = strs[i].toCharArray();
            Arrays.sort(chars);
            String sortStr = new String(chars);
            List<String> keys = hashtable.computeIfAbsent(sortStr, k -> new ArrayList<>());// sortStr 在 hashMap 中是否存在，不存在则执 lambda
            keys.add(strs[i]);
        }

        return new ArrayList<>(hashtable.values());

    }


    /**
     * 如果是变位词，则它们的字母和出现次数是相等的
     * ，因此把每个串的每个字母和该字母出现次数作为hash表的键可确定变位词
     *
     * @param strs
     * @return java.util.List<java.util.List < java.lang.String>>
     * @author tqyao
     * @create 2023/9/17 10:22
     */
    public List<List<String>> groupAnagrams300(String[] strs) {
        HashMap<String, List<String>> hashMap = new HashMap<>();
        for (String str : strs) {
            char[] chs = str.toCharArray();
            int[] mappingArray = new int[26];
            // 以字母 ascii 做数组索引，字符串中出现一次该字符就+1
            for (char ch : chs) {
                mappingArray[ch - 97]++;
            }

            // 按顺序记录每个字母和其出现次数
            StringBuilder stringBuilder = new StringBuilder();
            for (int i = 0; i < mappingArray.length; i++) {
                if (mappingArray[i] != 0) {// != 0 该 ascii 对应字母出现过至少 1 次
                    // 记录字母
                    stringBuilder.append((char) ('a' + i));
                    // 记录其出现次数
                    stringBuilder.append(mappingArray[i]);
                }
            }
            String s = stringBuilder.toString();

            List<String> orDefault = hashMap.getOrDefault(s, new ArrayList<>());    // s 键对应在 Map 中有值则返回，否则返回第二个参数
            orDefault.add(str);
            hashMap.put(s, orDefault);
        }
        return new ArrayList<>(hashMap.values());
    }

    class StrMappingArray {
        int[] strArray = new int[26];

        public void toMapArray(String str) {
            char[] chs = str.toCharArray();
            for (char c : chs) {
                strArray[c - 97]++;
            }
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            StrMappingArray that = (StrMappingArray) o;
            return Arrays.equals(strArray, that.strArray);
        }

        @Override
        public int hashCode() {
            return Arrays.hashCode(strArray);
        }


    }

    /**
     * 构造一个字符串与数组映射对象，通过重写 hashcode equal 方法后，再放入 hashMap 可确定 变位词
     *
     * @param strs
     * @return java.util.List<java.util.List < java.lang.String>>
     * @author tqyao
     * @create 2023/9/17 10:25
     */
    public List<List<String>> groupAnagrams200(String[] strs) {
        HashMap<StrMappingArray, List<String>> hashMap = new HashMap<>();

        for (String str : strs) {
            StrMappingArray strMappingArray = new StrMappingArray();
            strMappingArray.toMapArray(str);
            List<String> strings = hashMap.computeIfAbsent(strMappingArray, (key) -> new ArrayList<>());

//            List<String> strings = hashMap.get(strMappingArray);
//            if (strings == null) {
//                strings = new ArrayList<>();
//            }
            strings.add(str);
            hashMap.put(strMappingArray, strings);
        }
        return new ArrayList<>(hashMap.values());
    }


    public List<List<String>> groupAnagrams101(String[] strs) {
        HashMap<String, List<String>> hashtable = new HashMap<>();

        for (int i = 0; i < strs.length; i++) {
            String sortStr = Arrays.stream(strs[i].split("")).sorted().collect(Collectors.joining());
            List<String> keys = hashtable.computeIfAbsent(sortStr, k -> new ArrayList<>());// sortStr 在 hashMap 中是否存在，不存在则执 lambda
            keys.add(strs[i]);
        }

        return new ArrayList<>(hashtable.values());

    }

    /**
     * 判断是否是变位词
     *
     * @param s1
     * @param s2
     * @return boolean
     * @author tqyao
     * @create 2023/9/16 20:42
     */
    public static boolean judgeDegeneration(String s1, String s2) {
//        if (s1 == null || s2 == null){
//            return false;
//        }
//        if (s1.length() != s2.length()) {
//            return false;
//        }
//        if (s1.equals(s2)) {
//            return true;
//        }
        Set<String> collect = Arrays.stream(s1.split("")).collect(Collectors.toSet());
        Set<String> collect2 = Arrays.stream(s2.split("")).collect(Collectors.toSet());

//        System.out.println(collect.containsAll(collect2));

        return collect.containsAll(collect2);
    }


    @Test
    public void test04() {

//        String[] strings = {"eat", "tea", "tan", "ate", "nat", "bat"};
        String[] strings = {"eeat", "eat"};
        new HashSolution().groupAnagrams300(strings);

//        new StrMappingArray().toMapArray("abc");

//        new Solution().groupAnagrams100(strings);

//        Hashtable hashtable = new Hashtable();
//        System.out.println(hashtable.get("23"));

//        String[] strings = {"a", "b"};
//        System.out.println(Arrays.stream(strings).collect(Collectors.joining()));

//        ArrayList<Integer> arrayList = Lists.newArrayList(1, 2, 3, 4);
//        int i = 0;
//        for (Integer integer : arrayList) {
//            System.out.println(++i);
//            arrayList.remove(i);
//        }

//        judgeDegeneration("abc",new String("abc"));

//        ArrayList<Integer> objects = new ArrayList<>();
//        objects.add(1);
//        objects.add(1);
//        System.out.println(objects);
//        objects.add(3);
//        System.out.println(objects.remove(1));
//
//        System.out.println(objects.get(1));


    }

    /**
     * https://leetcode.cn/problems/longest-substring-without-repeating-characters/
     * 输入: s = "pwwkew"
     * 输出: 3
     * 解释: 因为无重复字符的最长子串是 "wke"，所以其长度为 3。
     * 请注意，你的答案必须是 子串 的长度，"pwke" 是一个子序列，不是子串。
     * <p>
     * p
     * pw
     * w
     * wk
     * wke
     * w
     * <p>
     * abcabcbb
     * a
     * ab
     * abc
     * bca
     * cab
     * abc
     * cb
     * b
     * <p>
     * 防止指针回溯 begin = Math.max(begin, hashMap.get(ch) + 1);
     * 举例:
     * abba
     * a
     * ab
     * b
     * ba
     *
     * @param s
     * @return int
     * @author tqyao
     * @create 2023/9/15 10:14
     */
    public int lengthOfLongestSubstring(String s) {
        Map<Character, Integer> hashMap = new HashMap<>();
        int begin = 0;
        int maxLen = 0;

        for (int end = 0; end < s.length(); end++) {// 移动右指针
            char ch = s.charAt(end);
            if (hashMap.containsKey(s.charAt(end))) {
                // 遇到重复字符，移动左指针，左指针=取出重复字符索引+1。Math 避免指针回溯
                begin = Math.max(begin, hashMap.get(ch) + 1);
            }
            hashMap.put(ch, end);
            maxLen = Math.max(maxLen, end - begin + 1);
            System.out.println(s.substring(begin, end + 1));
        }
        return maxLen;
    }

    /**
     * 滑动窗口
     * 定义左边界和右边界指针
     * 1. 不断移动右指针
     * - 不重复字符，加入 set，右指针++
     * - 遇到重复字符，则此时为该子串不重复最大长度，停止移动右指针，左指针右移动 1 位
     *
     * @param s
     * @return int
     * @author tqyao
     * @create 2023/9/16 10:32
     */
    public int lengthOfLongestSubstring200(String s) {
        // 哈希集合，记录每个字符是否出现过
        Set<Character> occ = new HashSet<Character>();
        int n = s.length();
        // 右指针，初始值为 -1，相当于我们在字符串的左边界的左侧，还没有开始移动
        int rk = -1, ans = 0;
        for (int i = 0; i < n; ++i) {
            if (i != 0) {
                // 左指针向右移动一格，移除一个字符
                occ.remove(s.charAt(i - 1));
            }
            while (rk + 1 < n && !occ.contains(s.charAt(rk + 1))) {
                // 不断地移动右指针
                occ.add(s.charAt(rk + 1));
                ++rk;
            }
            // 第 i 到 rk 个字符是一个极长的无重复字符子串
            ans = Math.max(ans, rk - i + 1);
            System.out.println(s.substring(i, rk + 1));

        }
        return ans;
    }


    public int lengthOfLongestSubstring202(String s) {
        HashSet<Character> set = new HashSet<>();
        int rp = -1;
        int maxLen = 0;
        int len = s.length();
        for (int i = 0; i < len; i++) {

            if (i != 0) {
                set.remove(s.charAt(i - 1));
            }

            while (rp + 1 < len && !set.contains(s.charAt(rp + 1))) {
                set.add(s.charAt(rp + 1));
                rp++;
            }

            maxLen = Math.max(maxLen, rp - i + 1);
        }
        return maxLen;
    }

    public int lengthOfLongestSubstring201(String s) {
        // 哈希集合，记录每个字符是否出现过
        Set<Character> occ = new HashSet<>();
        // 右指针
        int rp = -1;
        int maxLen = 0;
        int len = s.length();
        for (int i = 0; i < len; ++i) {
            if (i != 0) {
                occ.remove(s.charAt(i - 1));
            }

            while (rp + 1 < len && !occ.contains(s.charAt(rp + 1))) {
                occ.add(s.charAt(rp + 1));
                rp++;
            }

            System.out.println(s.substring(i, rp + 1));
            maxLen = Math.max(maxLen, rp - i + 1);
        }
        return maxLen;
    }


    public int lengthOfLongestSubstring101(String s) {
        Map<Character, Integer> hashMap = new HashMap<>();
        int begin = 0;
        int end = 0;
        int maxLen = 0;
        for (int i = 0; i < s.length(); i++) {
            char ch = s.charAt(i);
            if (hashMap.containsKey(s.charAt(i))) {
                begin = hashMap.get(ch) + 1;
                hashMap.put(ch, end++);
            } else {
                hashMap.put(ch, end++);
            }
            maxLen = Math.max(maxLen, i - begin + 1);
            System.out.println(s.substring(begin, end));
        }

        return maxLen;
    }


    public int lengthOfLongestSubstring103(String s) {
        Hashtable<Character, Integer> hashtable = new Hashtable<>();
        int end, start = 0, maxLen = 0;

        for (end = 0; end < s.length(); end++) {
            char ch = s.charAt(end);
            if (hashtable.containsKey(ch)) {
                start = Math.max(start, hashtable.get(ch) + 1);
            }
            hashtable.put(ch, end);
            maxLen = Math.max(maxLen, end - start + 1);
        }
        return maxLen;
    }

    @Test
    public void test03() {
        new HashSolution().lengthOfLongestSubstring("abcabcbb");
        System.out.println("====");
        new HashSolution().lengthOfLongestSubstring200("abcabcbb");
    }


    /**
     * https://leetcode.cn/problems/two-sum/?envType=study-plan-v2&envId=top-100-liked
     *
     * @param nums
     * @param target
     * @return int[]
     * @author tqyao
     * @create 2023/9/15 09:29
     */
    public int[] twoSum(int[] nums, int target) {
        for (int i = 0; i < nums.length - 1; i++) {
            for (int j = i + 1; j < nums.length; j++) {
                if (nums[i] + nums[j] == target) {
                    return new int[]{i, j};
                }
            }
        }
        return new int[]{};
    }

    public static int[] twoSum2(int[] nums, int target) {
        Hashtable<Integer, Integer> hashtable = new Hashtable<>();
        for (int i = 0; i < nums.length; i++) {
            if (hashtable.containsKey(target - nums[i])) {
                return new int[]{hashtable.get(target - nums[i]), i};
            }
            hashtable.put(nums[i], i);
        }
        return new int[]{0};
    }


    @Test
    public void test() {
        int[] ints = Stream.of(2, 7, 11, 15).mapToInt(Integer::intValue).toArray();
        System.out.println(Arrays.toString(twoSum2(ints, 9)));
    }

}