//
//  BBB.m
//  FB
//
//  Created by yangyong on 2021/4/22.
//

#import "BBB.h"

@implementation BBB

- (NSString *)funB
{
    return @"BBB";
}

- (NSString *)funBCallA
{
    AAA *a = [[AAA alloc] init];
    return [a funA];
}

@end
