dp1 = open('data_part1','r')
dp2 = open('data_part2','r')

data_part1_lines = dp1.read().split('\n')
data_part2_lines = dp2.read().split('\n')
if len(data_part1_lines) != len(data_part2_lines):
	print 'Data part files are corrupted!'
else :
	outfile = open('outfile_merged','w')
	for i in range(len(data_part1_lines)):
		outfile.write(data_part1_lines[i]+'\t'+data_part2_lines[i]+'\n')
	outfile.close()
	print 'outfile_merged written'

		
	

