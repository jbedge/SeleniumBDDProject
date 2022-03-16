package com.interviewpractice;

public class TryCatchFinally {

public static int occrance=0;
    public static int minlength;
    public static void main(String[] args) {

        try {

            throw new Exception();
//            System.out.println("inside cathc");
        }catch (Exception e){
            System.out.println("inside exception.");
            return;
//            System.exit(1);

        }
        finally {
            System.out.println("insdie finally.");
        }
        }

//        String[] names={"jagadeesh"};
//        minlength=names[0].length();
//        List<String > na=Arrays.asList(names);
//        Map map=na.stream().collect(Collectors.toMap(Function.identity(), i->1,Math::addExact));
//
//
//        map.forEach((k,v)->{
//            if((int)v>occrance){
//                occrance=(int) v;
//            }
//        });
//
//        map.forEach((k,v)->{
//            if((int)v==occrance ){
//                if(((String)k).length()<minlength)
//                    minlength=((String)k).length();
//            }
//        });
//
//        map.forEach((k,v)->{
//                if(((String)k).length()==minlength&&(int)v==occrance)
//                System.out.println("key :"+k+ " occurances:"+v);
//        });
//
//    }


}
